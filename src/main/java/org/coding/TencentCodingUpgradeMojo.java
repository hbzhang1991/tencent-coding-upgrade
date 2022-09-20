package org.coding;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.alibaba.fastjson.JSONObject;
import org.apache.http.message.BasicHeader;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.coding.http.HttpClientUtil;
import org.coding.model.EnvsItem;
import org.coding.model.RequestParameter;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Logger;

/**
 *    腾讯云-CODING-持续集成插件
 *
 * Goal  upgrade project by tencent coding platform
 *
 * @goal upgrade
 *
 * @phase process-sources
 */
public class TencentCodingUpgradeMojo extends AbstractMojo
{
    private static final Logger logger     = Logger.getLogger(TencentCodingUpgradeMojo.class.getName());
    /**
     * CODING userName. 项目设置-开发者选项-项目令牌-用户名
     * @parameter expression="${userName}"
     * @required
     */
    private String userName;
    /**
     * CODING secretKey.项目设置-开发者选项-项目令牌 密码
     * @parameter expression="${secretKey}"
     * @required
     */
    private String secretKey;

    /**
     * CODING requestUrl.持续集成-构建计划-设置-触发规则-API触发
     * @parameter expression="${requestUrl}"
     * @required
     */
    private String requestUrl;
    /**
     * CODING ref. 不传默认master分支
     * @parameter expression="${ref}"
     * @required false
     */
    private String ref;

    /**
     * CODING envsItems.
     * @parameter expression="${envsItems}"
     * @required false
     */
    private List<EnvsItem> envsItems;

    public void execute() throws MojoExecutionException
    {
        logger.info("tencent-coding-upgrade 开始构建-升级 ----- ");
        RequestParameter parameter = new RequestParameter();
        parameter.setRef(ref);
        parameter.setEnvs(envsItems);
        if("".equals(userName) || "".equals(secretKey) || "".equals(requestUrl))
        {
            logger.info("userName or secretKey or requestUrl is not null or empty");
            return;
        }
        //Basic Auth认证方式
        String key = userName.concat(":").concat(secretKey);
        //base64编码
        byte[] authEncBytes = Base64.getEncoder().encode(key.getBytes(StandardCharsets.UTF_8));
        String authStringEnc = new String(authEncBytes);
        List<BasicHeader> list = new ArrayList<>();
        list.add(new BasicHeader("Authorization","Basic "+authStringEnc));
        list.add(new BasicHeader("Content-Type", "application/json"));
        try {
            HttpClientUtil.getInstance(list).doPost(requestUrl,JSONObject.toJSONString(parameter));
        } catch (Exception e) {
            logger.info("tencent-coding-upgrade 升级失败 :"+e.getMessage());
        }finally{
            logger.info("tencent-coding-upgrade 构建-升级-结束 ----- ");
        }

    }
}
