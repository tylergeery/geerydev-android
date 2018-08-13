package com.geerydev.tyler.geerydev.fragment

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.Toast
import com.geerydev.tyler.geerydev.R
import com.geerydev.tyler.geerydev.activity.MainActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import java.io.IOException

class AboutFragment: BaseFragment() {
    private lateinit var wv: WebView
    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            val mainActivity = activity as MainActivity
            mainActivity.setChecked(R.id.navigation_about)
            println(this.javaClass.toString() + ": Set Checked Navigation")
            createAboutWebView()
            println(this.javaClass.toString() + ": Created Web View")
            getAboutPage()
            println(this.javaClass.toString() + ": Kicked off about page observable")
        }
    }

    override fun onResume() {
        super.onResume()

        getAboutPage()
    }

    override fun onPause() {
        super.onPause()

        disposable?.dispose()
        //findViewById<ConstraintLayout>(R.id.container).removeView(wv)
    }

    fun createAboutWebView() {
        wv = WebView(this)
        val layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        val layout = findViewById<ConstraintLayout>(R.id.container);

        layout.addView(wv, layoutParams)
    }

    fun getAboutPage() {
        disposable = createAboutObservable()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { html -> wv.loadData(html, "text/html", "UTF-8") },
                        { error -> Toast.makeText(this, "About view not loaded: " + error.message, Toast.LENGTH_LONG).show() }
                )

    }

    fun createAboutObservable (): Observable<String> {
        return Observable.create { emitter ->
            try {
                println(this.javaClass.toString() + ": Fetching Geerydev About")
                val htmlDocument = Jsoup.connect("https://www.geerydev.com/about").get()
                val element = htmlDocument.select("#about-post_content").first()

                println(this.javaClass.toString() + ": Found post_content: " + element.toString())
                // replace body with selected element
                htmlDocument.body().empty().append(element.toString())

                println(this.javaClass.toString() + ": Setting document html")
                emitter.onNext(htmlDocument.toString())
            } catch (e: IOException) {
                emitter.onError(e)
            }

            emitter.onComplete()
        }
    }
}