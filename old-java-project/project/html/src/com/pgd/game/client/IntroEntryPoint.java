package com.pgd.game.client;

import com.badlogic.gdx.backends.gwt.SuperDevModeIndicator;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.Window;
import com.google.gwt.xhr.client.XMLHttpRequest;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class IntroEntryPoint implements EntryPoint {

    private static final LinkedList<String> toPreload = new LinkedList<String>();

    static Map<String, Float> loadingProgress = new HashMap<String, Float>();
    static int toLoad = 0;
    static int loaded = 0;

    @Override
    public void onModuleLoad() {

        SuperDevModeIndicator superDevModeIndicator = GWT.create(SuperDevModeIndicator.class);
        if (!superDevModeIndicator.isSuperDevMode()) {
            toPreload.add(getUrl(1));
            toLoad = toPreload.size();

            while (!toPreload.isEmpty()) {
                load(toPreload.remove(0));
            }
        } else {
            HtmlLauncher htmlLauncher = new HtmlLauncher();
            htmlLauncher.onModuleLoad();
        }
    }

    private String getUrl(int fragment) {
        return GWT.getModuleBaseURL() + "deferredjs/"
                + GWT.getPermutationStrongName() + "/" + fragment + ".cache.js";
    }


    public void handleSideLoadProgress(String path, float progress) {
        Element element = Document.get().getElementById("loading-meter");
        float overall = 0;

        loadingProgress.put(path, progress);

        for (Float f : loadingProgress.values()) {
            overall += ((1 / (float) toLoad) * f) * 100;
        }

        element.getStyle().setWidth(overall, Style.Unit.PCT);
    }

    public void handleSideLoadComplete(String path) {
//    	Window.alert(toLoad + " " + loaded + " " + path);
        loaded++;
        if (loaded == toLoad) {
            GWT.runAsync(new RunAsyncCallback() {
                public void onFailure(Throwable caught) {
                    Window.alert("Download failed");
                }

                public void onSuccess() {
                    try {
//                    	Window.alert("Launching the game");
                        HtmlLauncher htmlLauncher = new HtmlLauncher();
                        htmlLauncher.onModuleLoad();
                    } catch (Throwable t) {
                        Window.alert(t.getMessage());
                    }
                }
            });
        }
    }

//	private native void consoleLog(String str) /*-{
//		console.log("(HtmlGameServer) " + str);
//	}-*/;

	public void load(final String url) {
//		Window.alert("Load:" + url);
        XMLHttpRequest request = XMLHttpRequest.create();
        request.setOnReadyStateChange(xhr -> {
            if (xhr.getReadyState() == XMLHttpRequest.DONE) {
                if (xhr.getStatus() == 200) {
                    handleSideLoadComplete(url);
                }
            }
        });
//        setOnProgress(url, request, this);
        request.open("GET", url);
        request.setResponseType(XMLHttpRequest.ResponseType.ArrayBuffer);
        request.send();
    }

    private native static void setOnProgress(String path, XMLHttpRequest req, IntroEntryPoint point) /*-{
        req.onprogress = $entry(function(evt) {
		    point.@com.pgd.game.client.IntroEntryPoint::handleSideLoadProgress(Ljava/lang/String;F)(path, evt.loaded / evt.total);
		});
	}-*/;

}
