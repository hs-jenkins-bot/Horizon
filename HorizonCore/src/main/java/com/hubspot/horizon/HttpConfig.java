package com.hubspot.horizon;

import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;
import com.hubspot.horizon.HttpRequest.Options;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.concurrent.TimeUnit;

public class HttpConfig {
  private final int maxConnections;
  private final int maxConnectionsPerHost;
  private final int connectTimeoutSeconds;
  private final int requestTimeoutSeconds;
  private final int defaultKeepAliveSeconds;
  private final int maxRedirects;
  private final String userAgent;
  private final boolean acceptAllSSL;
  private final boolean followRedirects;
  private final boolean rejectRelativeRedirects;
  private final int maxRetries;
  private final int initialRetryBackoffSeconds;
  private final int maxRetryBackoffSeconds;
  private final RetryStrategy retryStrategy;

  private HttpConfig(int maxConnections,
                     int maxConnectionsPerHost,
                     int connectTimeoutSeconds,
                     int requestTimeoutSeconds,
                     int defaultKeepAliveSeconds,
                     int maxRedirects,
                     String userAgent,
                     boolean acceptAllSSL,
                     boolean followRedirects,
                     boolean rejectRelativeRedirects,
                     int maxRetries,
                     int initialRetryBackoffSeconds,
                     int maxRetryBackoffSeconds,
                     RetryStrategy retryStrategy) {
    this.maxConnections = maxConnections;
    this.maxConnectionsPerHost = maxConnectionsPerHost;
    this.connectTimeoutSeconds = connectTimeoutSeconds;
    this.requestTimeoutSeconds = requestTimeoutSeconds;
    this.defaultKeepAliveSeconds = defaultKeepAliveSeconds;
    this.maxRedirects = maxRedirects;
    this.userAgent = userAgent;
    this.acceptAllSSL = acceptAllSSL;
    this.followRedirects = followRedirects;
    this.rejectRelativeRedirects = rejectRelativeRedirects;
    this.maxRetries = maxRetries;
    this.initialRetryBackoffSeconds = initialRetryBackoffSeconds;
    this.maxRetryBackoffSeconds = maxRetryBackoffSeconds;
    this.retryStrategy = retryStrategy;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public int getMaxConnections() {
    return maxConnections;
  }

  public int getMaxConnectionsPerHost() {
    return maxConnectionsPerHost;
  }

  public int getConnectTimeoutMillis() {
    return Ints.checkedCast(TimeUnit.SECONDS.toMillis(connectTimeoutSeconds));
  }

  public int getRequestTimeoutMillis() {
    return Ints.checkedCast(TimeUnit.SECONDS.toMillis(requestTimeoutSeconds));
  }

  public int getDefaultKeepAliveMillis() {
    return Ints.checkedCast(TimeUnit.SECONDS.toMillis(defaultKeepAliveSeconds));
  }

  public int getMaxRedirects() {
    return maxRedirects;
  }

  public @Nonnull String getUserAgent() {
    return userAgent;
  }

  public boolean isAcceptAllSSL() {
    return acceptAllSSL;
  }

  public boolean isFollowRedirects() {
    return followRedirects;
  }

  public boolean isRejectRelativeRedirects() {
    return rejectRelativeRedirects;
  }

  public @Nonnull
  Options getOptions() {
    Options options = new Options();

    options.setMaxRetries(maxRetries);
    options.setInitialRetryBackoffSeconds(initialRetryBackoffSeconds);
    options.setMaxRetryBackoffSeconds(maxRetryBackoffSeconds);
    options.setRetryStrategy(retryStrategy);

    return options;
  }

  @ParametersAreNonnullByDefault
  public static class Builder {
    private int maxConnections = 100;
    private int maxConnectionsPerHost = 25;
    private int connectTimeoutSeconds = 1;
    private int requestTimeoutSeconds = 30;
    private int defaultKeepAliveSeconds = 10;
    private int maxRedirects = 10;
    private String userAgent = "Horizon/0.0.1";
    private boolean acceptAllSSL = false;
    private boolean followRedirects = true;
    private boolean rejectRelativeRedirects;
    private int maxRetries = 3;
    private int initialRetryBackoffSeconds = 1;
    private int maxRetryBackoffSeconds = 30;
    private RetryStrategy retryStrategy = RetryStrategy.DEFAULT;

    private Builder() { }

    public Builder setMaxConnections(int maxConnections) {
      this.maxConnections = maxConnections;
      return this;
    }

    public Builder setMaxConnectionsPerHost(int maxConnectionsPerHost) {
      this.maxConnectionsPerHost = maxConnectionsPerHost;
      return this;
    }

    public Builder setConnectTimeoutSeconds(int connectTimeoutSeconds) {
      this.connectTimeoutSeconds = connectTimeoutSeconds;
      return this;
    }

    public Builder setRequestTimeoutSeconds(int requestTimeoutSeconds) {
      this.requestTimeoutSeconds = requestTimeoutSeconds;
      return this;
    }

    public Builder setDefaultKeepAliveSeconds(int defaultKeepAliveSeconds) {
      this.defaultKeepAliveSeconds = defaultKeepAliveSeconds;
      return this;
    }

    public Builder setMaxRedirects(int maxRedirects) {
      this.maxRedirects = maxRedirects;
      return this;
    }

    public Builder setUserAgent(String userAgent) {
      this.userAgent = Preconditions.checkNotNull(userAgent);
      return this;
    }

    public Builder setAcceptAllSSL(boolean acceptAllSSL) {
      this.acceptAllSSL = acceptAllSSL;
      return this;
    }

    public Builder setFollowRedirects(boolean followRedirects) {
      this.followRedirects = followRedirects;
      return this;
    }

    public Builder setRejectRelativeRedirects(boolean rejectRelativeRedirects) {
      this.rejectRelativeRedirects = rejectRelativeRedirects;
      return this;
    }

    public Builder setMaxRetries(int maxRetries) {
      this.maxRetries = maxRetries;
      return this;
    }

    public Builder setInitialRetryBackoffSeconds(int initialRetryBackoffSeconds) {
      this.initialRetryBackoffSeconds = initialRetryBackoffSeconds;
      return this;
    }

    public Builder setMaxRetryBackoffSeconds(int maxRetryBackoffSeconds) {
      this.maxRetryBackoffSeconds = maxRetryBackoffSeconds;
      return this;
    }

    public Builder setRetryStrategy(RetryStrategy retryStrategy) {
      this.retryStrategy = Preconditions.checkNotNull(retryStrategy);
      return this;
    }

    public HttpConfig build() {
      return new HttpConfig(maxConnections,
              maxConnectionsPerHost,
              connectTimeoutSeconds,
              requestTimeoutSeconds,
              defaultKeepAliveSeconds,
              maxRedirects,
              userAgent,
              acceptAllSSL,
              followRedirects,
              rejectRelativeRedirects,
              maxRetries,
              initialRetryBackoffSeconds,
              maxRetryBackoffSeconds,
              retryStrategy);
    }
  }
}