# tencent-coding-upgrade
一款调用腾讯云 CODING进行自动化构建和部署的升级插件(https://coding.net/)

CSND博客介绍：https://blog.csdn.net/zhb2010zhb/article/details/126608759

### 前置条件：
* 登录https://coding.net/网站 没有注册的可以注册一下，免费的服务器帮你构建项目
* 创建项目
* 持续集成-构建计划 新建一个构建计划

### 使用方法：
* 下载项目后本地运行 mvn install,会自动上传到本地maven仓库中，也可以放到私服上面
* 在需要使用的项目的pom.xml文件中加入
~~~
 <plugin>
                <!-- 腾讯云-CODING-持续集成插件 -->
                <groupId>org.coding</groupId>
                <artifactId>tencent-coding-upgrade</artifactId>
                <version>1.0-SNAPSHOT</version>
                <configuration>
                    <!-- 项目设置-开发者选项-项目令牌-用户名 -->
                    <userName>ptuf0njxfidn</userName>
                    <!-- 项目设置-开发者选项-项目令牌 密码 -->
                    <secretKey>6e93e5459dc0d0ddd2e07f814b6b03654bc48125</secretKey>
                    <!-- 持续集成-构建计划-设置-触发规则-API触发- -->
                    <requestUrl>https://xiaoyunduo.coding.net/api/cci/job/1527676/trigger</requestUrl>
                    <!-- 构建目标-git分支 默认master -->
                    <ref>master</ref>
                    <!-- 启动参数 此处可不配置，不配置则取默认的，在触发规则旁边的tab页变量与缓存里面有 -->
                    <!-- <envsItems>
                        <item>
                            <name>DOCKERFILE_PATH</name>
                            <value>Dockerfile</value>
                        </item>
                        <item>
                            <name>DOCKER_IMAGE_NAME</name>
                            <value>yun-pos</value>
                        </item>
                    </envsItems>-->
                </configuration>
            </plugin>
~~~ 

* 这样在maven - plugins 里面 就能看到 tencent-coding-upgrade插件，双击就会自动进行升级了

