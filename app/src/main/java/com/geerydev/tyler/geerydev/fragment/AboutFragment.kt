package com.geerydev.tyler.geerydev.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import java.io.IOException

class AboutFragment: BaseFragment() {
    private var disposable: Disposable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return WebView(activity)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getAboutPage()
    }

    override fun onResume() {
        super.onResume()

        getAboutPage()
    }

    override fun onPause() {
        super.onPause()

        disposable?.dispose()
    }

    fun getAboutPage() {
        disposable = createAboutObservable()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { html -> (view as WebView).loadData(html, "text/html", "UTF-8") },
                        { error -> showError(error.message) }
                )

    }

    fun createAboutObservable (): Observable<String> {
        return Observable.create { emitter ->
            try {
                val htmlDocument = Jsoup.connect("https://www.geerydev.com/about").get()
                val element = htmlDocument.select("#about-content").first()

                // replace body with selected element
                htmlDocument.body().empty().append(element.toString())

                emitter.onNext(htmlDocument.toString())
            } catch (e: IOException) {
                emitter.onError(e)
            }

            emitter.onComplete()
        }
    }
}