/*
 * Copyright (c) 2020 pig4cloud Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.xiaowu.behappy.common.core.result;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import org.apache.hc.core5.http.HttpStatus;
import org.xiaowu.behappy.common.core.enums.BizCodeEnum;

import java.io.Serial;
import java.util.HashMap;
import java.util.Map;

import static org.xiaowu.behappy.common.core.enums.BizCodeEnum.OK;


/**
 * @author xiaowu
 * 响应信息主体
 */
public class R extends HashMap<String, Object> {

    @Serial
    private static final long serialVersionUID = 1L;

    public R setData(Object data) {
        put("data", data);
        return this;
    }

    public R setCode(int code) {
        put("code", code);
        return this;
    }

    public R setMsg(String msg) {
        put("msg", msg);
        return this;
    }

    public Object getData() {
        return this.get("data");
    }

    public int getCode() {
        return (Integer) this.get("code");
    }

    public String getMsg() {
        return ((String) this.get("msg"));
    }

    /**
     * 利用fastjson进行反序列化
     * @param typeReference
     * @return
     * @param <T>
     */
    public <T> T getData(TypeReference<T> typeReference) {
        //默认是map
        Object data = get("data");
        String jsonString = JSON.toJSONString(data);
        return JSON.parseObject(jsonString, typeReference);
    }

    /**
     * 利用fastjson进行反序列化
     * @param key
     * @param typeReference
     * @return
     * @param <T>
     */
    public <T> T getData(String key, TypeReference<T> typeReference) {
        // 默认是map
        Object data = get(key);
        String jsonString = JSON.toJSONString(data);
        return JSON.parseObject(jsonString, typeReference);
    }

    public R() {
        setCode(OK.getCode());
        setMsg(OK.getMessage());
    }

    public static R error() {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
    }

    public static R error(String msg) {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
    }

    public static R error(Throwable throwable) {
        return error(BizCodeEnum.UNKNOWN_EXCEPTION.getCode(), throwable.getMessage());
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }

    public static R ok(String msg) {
        R r = new R();
        r.setMsg(msg);
        return r;
    }

    public static R ok(Map<String, Object> map) {
        R r = new R();
        r.putAll(map);
        return r;
    }

    public static R ok() {
        return new R();
    }

    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }

}
