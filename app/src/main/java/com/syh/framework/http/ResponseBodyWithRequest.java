package com.syh.framework.http;

import io.reactivex.annotations.Nullable;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.BufferedSource;

public class ResponseBodyWithRequest extends ResponseBody {

    /**
     * Use a string to avoid parsing the content type until needed. This also defers problems caused
     * by malformed content types.
     */
    private final @Nullable
    String contentTypeString;
    private final long contentLength;
    private final BufferedSource source;

    public ResponseBodyWithRequest(
            @Nullable String contentTypeString, long contentLength, BufferedSource source) {
        this.contentTypeString = contentTypeString;
        this.contentLength = contentLength;
        this.source = source;
    }

    @Override public MediaType contentType() {
        return contentTypeString != null ? MediaType.parse(contentTypeString) : null;
    }

    @Override public long contentLength() {
        return contentLength;
    }

    @Override public BufferedSource source() {
        return source;
    }
}
