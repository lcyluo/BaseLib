package com.lcy.base.core.common;

import android.support.annotation.NonNull;

import com.lcy.base.core.data.net.ApiConstant;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 配置Http请求以及响应码
 *
 * @author lh
 */
public class HttpConfig {
    /**
     * 是否打印http请求log信息
     */
    private final boolean showLog;
    /**
     * 打印Http请求log信息
     */
    private final HttpLoggingInterceptor.Logger logger;
    /**
     * 定义OkHttp自定义拦截器
     */
    private final List<Interceptor> interceptors;
    /**
     * 连接超时时间,单位秒
     */
    private final long connectTimeout;
    /**
     * 读超时时间,单位秒
     */
    private final long readTimeout;
    /**
     * 写超时时间,单位秒
     */
    private final long writeTimeout;
    /**
     * 是否可以启用代理获取app接口信息
     */
    private final boolean canProxy;
    /**
     * Retrofit的base url
     */
    @NonNull
    private final String baseUrl;

    private HttpConfig(@NonNull Builder builder) {
        this.showLog = builder.showLog;
        this.logger = builder.logger;
        this.interceptors = builder.interceptors;
        this.connectTimeout = builder.connectTimeout;
        this.readTimeout = builder.readTimeout;
        this.writeTimeout = builder.writeTimeout;
        this.canProxy = builder.canProxy;
        this.baseUrl = builder.baseUrl;
    }

    public boolean isShowLog() {
        return showLog;
    }

    public HttpLoggingInterceptor.Logger getLogger() {
        return logger;
    }

    public List<Interceptor> getInterceptors() {
        return interceptors;
    }

    public long getConnectTimeout() {
        return connectTimeout;
    }

    public long getReadTimeout() {
        return readTimeout;
    }

    public long getWriteTimeout() {
        return writeTimeout;
    }

    public boolean isCanProxy() {
        return canProxy;
    }

    @NonNull
    public String getBaseUrl() {
        return baseUrl;
    }

    public static class Builder {
        private boolean showLog;
        private HttpLoggingInterceptor.Logger logger;
        private List<Interceptor> interceptors;
        private long connectTimeout;
        private long readTimeout;
        private long writeTimeout;
        private boolean canProxy;
        private String baseUrl;

        public Builder() {
            this.showLog = false;
            this.logger = null;
            this.interceptors = null;
            this.connectTimeout = Constants.APP_API_TIMEOUT;
            this.readTimeout = Constants.APP_API_TIMEOUT;
            this.writeTimeout = Constants.APP_API_TIMEOUT;
            this.canProxy = true;
            this.baseUrl = null;
        }

        public Builder showLog(boolean showLog) {
            this.showLog = showLog;
            return this;
        }

        public Builder logger(HttpLoggingInterceptor.Logger logger) {
            this.logger = logger;
            return this;
        }

        public Builder addInterceptors(List<Interceptor> interceptors) {
            this.interceptors = interceptors;
            return this;
        }

        public Builder connectTimeout(long connectTimeout) {
            if (connectTimeout > 60L) {
                connectTimeout = 60L;
            }
            this.connectTimeout = connectTimeout;
            return this;
        }

        public Builder readTimeout(long readTimeout) {
            if (connectTimeout > 60L) {
                connectTimeout = 60L;
            }
            this.readTimeout = readTimeout;
            return this;
        }

        public Builder writeTimeout(long writeTimeout) {
            if (connectTimeout > 60L) {
                connectTimeout = 60L;
            }
            this.writeTimeout = writeTimeout;
            return this;
        }

        public Builder canProxy(boolean canProxy) {
            this.canProxy = canProxy;
            return this;
        }

        @NonNull
        public Builder baseUrl(@NonNull String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        @NonNull
        public HttpConfig build() {
            return new HttpConfig(this);
        }
    }


}
