# IpFilter

通过自定义注解+AOP方式实现IP黑/白名单过滤。filter-type默认BlackList（黑名单），ips中的ip拦截，WhiteList（白名单），ips中的ip允许通过。

## 引用

### （1）下载jar包
下载target/ipfilter-1.0.4.jar，放置到项目resource/lib下
pom.xml引用
```
<dependency>
    <groupId>com.llf</groupId>
    <artifactId>ipfilter</artifactId>
    <version>1.0.4</version>
    <scope>system</scope>
    <systemPath>${project.basedir}/src/main/resources/lib/ipfilter-1.0.4.jar</systemPath>
</dependency>
```

### （2）上传到私有库
```
<dependency>
    <groupId>com.llf</groupId>
    <artifactId>ipfilter</artifactId>
    <version>1.0.4</version>
</dependency>
```

## 增加配置
filter-type默认BlackList（黑名单），ips中的ip拦截，WhiteList（白名单），ips中的ip允许通过。
```
llf:
  ipfilter:
    filter-type: WhiteList
    ips: 127.0.0.1,localhost
```

## 启动类
增加扫描
```
@ComponentScan(basePackages = {"com.llf.*"})
```

## 接口使用
直接在接口上增加@IpFilter注解
```
@IpFilter
@GetMapping(value = "testFilter/{uid}", produces = "application/json;charset=UTF-8")
public ResultJson<Test> testFilter(@PathVariable String uid, HttpServletRequest request) {
    ···
}
```

## 拦截效果
接口返回
```
{"status": 1, "msg": ip为在黑名单中, "data": "IpFilter"}

{"status": 1, "msg": ip为不在白名单中, "data": "IpFilter"}

```

