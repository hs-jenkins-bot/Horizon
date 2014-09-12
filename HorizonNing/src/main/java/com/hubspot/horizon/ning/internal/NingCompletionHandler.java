package com.hubspot.horizon.ning.internal;

import com.hubspot.horizon.HttpRequest;
import com.hubspot.horizon.HttpResponse;
import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.Response;

import java.io.IOException;

public class NingCompletionHandler extends AsyncCompletionHandler<HttpResponse> {
  private final HttpRequest request;
  private final NingFuture future;
  private final NingRetryHandler retryHandler;

  public NingCompletionHandler(HttpRequest request, NingFuture future, NingRetryHandler retryHandler) {
    this.request = request;
    this.future = future;
    this.retryHandler = retryHandler;
  }

  @Override
  public HttpResponse onCompleted(Response ningResponse) throws Exception {
    HttpResponse response = new NingHttpResponse(request, ningResponse);
    if (retryHandler.shouldRetry(request, response)) {
      retryHandler.retry();
    } else {
      future.set(response);
    }
    return response;
  }

  @Override
  public void onThrowable(Throwable t) {
    IOException e = t instanceof IOException ? (IOException) t : new IOException(t);
    if (retryHandler.shouldRetry(request, e)) {
      retryHandler.retry();
    } else {
      future.setException(e);
    }
  }
}