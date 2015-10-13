/***********************************************************************************************************************
 * 
 * Copyright (C) 2014, 2015 by sunnsoft (http://www.sunnsoft.com)
 * http://www.sunnsoft.com/
 *
 ***********************************************************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 **********************************************************************************************************************/
package com.vanda.vandalibnetwork.fragment;

import java.util.Map;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.vanda.vandalibnetwork.daterequest.GsonRequest;
import com.vanda.vandalibnetwork.daterequest.RequestManager;

/**
 * @author vanda 伍中联 实现了Fragment 请求网络的封装
 *
 * @param <T>
 */
public abstract class BaseFragment<T> extends Fragment {

	@Override
	public void onStop() {
		super.onStop();
		RequestManager.cancelAll(BaseFragment.this);
	}

	protected void executeRequest(Request<T> request) {
		RequestManager.addRequest(request, BaseFragment.this);
	}

	protected void processData(T response) {
		RequestManager.cancelAll(BaseFragment.this);
	}

	protected void errorData(VolleyError volleyError) {
		RequestManager.cancelAll(BaseFragment.this);
	}

	protected abstract String getRequestUrl();

	protected abstract Class<T> getResponseDataClass();

	protected abstract Map<String, String> getParamMap();

	public void startExecuteRequest(int method) {
		executeRequest(new GsonRequest<T>(method, getRequestUrl(),
				getResponseDataClass(), getParamMap(),
				new Response.Listener<T>() {
					@Override
					public void onResponse(final T response) {
						processData(response);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						errorData(volleyError);
					}
				}));
	}
}