package com.fitmate.backend.media;

import org.kurento.client.KurentoClient;
import org.kurento.client.MediaPipeline;
import org.kurento.client.WebRtcEndpoint;

public class CallMediaPipeline {

    private MediaPipeline pipeline;
    private WebRtcEndpoint callerWebRtcEP;
    private WebRtcEndpoint calleeWebRtcEP;

    public CallMediaPipeline(KurentoClient kurento) {
        try {
            this.pipeline = kurento.createMediaPipeline();
            this.callerWebRtcEP = new WebRtcEndpoint.Builder(pipeline).build();
            this.calleeWebRtcEP = new WebRtcEndpoint.Builder(pipeline).build();

            this.callerWebRtcEP.connect(this.calleeWebRtcEP);
            this.calleeWebRtcEP.connect(this.callerWebRtcEP);
        } catch (Throwable t) {
            if (this.pipeline != null) {
                pipeline.release();
            }
        }
    }

    public String generateSdpAnswerForCaller(String sdpOffer) {
        return callerWebRtcEP.processOffer(sdpOffer);
    }

    public String generateSdpAnswerForCallee(String sdpOffer) {
        return calleeWebRtcEP.processOffer(sdpOffer);
    }

    public void release() {
        if (pipeline != null) {
            pipeline.release();
        }
    }

    public WebRtcEndpoint getCallerWebRtcEP() {
        return callerWebRtcEP;
    }

    public WebRtcEndpoint getCalleeWebRtcEP() {
        return calleeWebRtcEP;
    }

}