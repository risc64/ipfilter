package cn.llf.ipfilter;

import cn.llf.ipfilter.config.IpFilterProperties;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 *
 * @author 李良发
 * @version v1.0.0
 * @since 2023/2/2 18:05
 */
@Aspect
@Component
public class IpFilterAop {

    private Logger logger = LoggerFactory.getLogger(IpFilterAop.class);

    @Resource
    private IpFilterProperties ipFilterProperties;

    @Pointcut("@annotation(cn.llf.ipfilter.annotation.IpFilter)")
    public void ipFilterAopPoint() {

    }

    @Around("ipFilterAopPoint()")
    public Object doRouter(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("out ipfiltet");
        logger.info("IP验证");
        if (!isBlank(ipFilterProperties.getIps())) {
            if (!isBlank(ipFilterProperties.getFilterType())) {
                // 默认 黑名单模式
                ipFilterProperties.setFilterType("BlackList");
            }


            //HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
            //        .getRequest();

            if ("request" !=null) {
                //HttpServletResponse response = attributes.getResponse();
                String ip = "";
                if (!isBlank(ip)) {
                    String[] ipArray = ipFilterProperties.getIps().split(",");
                    if (ipArray != null && ipArray.length > 0) {
                        for(int i = 0; i < ipArray.length; i++ ) {
                            int index = ipArray[i].indexOf("*");
                            if(index != -1) {
                                if(ip.length() >= index) {
                                    String sub = ip.substring(0, index);
                                    if(sub.equals(ipArray[i].substring(0, index))) {
                                        // 匹配到
                                        if ("BlackList".equals(ipFilterProperties.getFilterType())) {
                                            // 黑名单，匹配到，拦截
                                            return returnObject(ipFilterProperties.getFilterType());
                                        } else {
                                            // 白名单，匹配到，放行
                                            return joinPoint.proceed();
                                        }
                                    } else {
                                        // 未匹配
                                        if ("BlackList".equals(ipFilterProperties.getFilterType())) {
                                            // 黑名单，未匹配，拦截
                                            return joinPoint.proceed();
                                        } else {
                                            // 白名单，未匹配，放行
                                            return returnObject(ipFilterProperties.getFilterType());
                                        }
                                    }
                                }
                            } else {
                                if(ip.equals(ipArray[i])) {
                                    // 匹配到
                                    if ("BlackList".equals(ipFilterProperties.getFilterType())) {
                                        // 黑名单，匹配到，拦截
                                        return returnObject(ipFilterProperties.getFilterType());
                                    } else {
                                        // 白名单，匹配到，放行
                                        return joinPoint.proceed();
                                    }
                                } else {
                                    // 未匹配
                                    if ("BlackList".equals(ipFilterProperties.getFilterType())) {
                                        // 黑名单，未匹配，拦截
                                        return joinPoint.proceed();
                                    } else {
                                        // 白名单，未匹配，放行
                                        return returnObject(ipFilterProperties.getFilterType());
                                    }
                                }
                            }
                        }
                    } else {
                        logger.info("ips为空");
                    }
                } else {
                    logger.info("未能获取IP");
                }
            } else {
                logger.info("非接口方法，HttpServletRequest为空，跳过");
                // 非接口方法，放行
                return joinPoint.proceed();
            }


        }
        logger.info("没有读取到要过滤的ips信息，跳过");
        // 没有读取到配置信息，放行
        return joinPoint.proceed();

    }

    private Object returnObject(String filterType) {
        String msg = "ip已拦截";
        if ("BlackList".equals(filterType)) {
            msg = "ip为黑名单";
        } else {
            msg = "ip为不在白名单中";
        }
        String result = "{\"status\": 1, \"msg\": " + msg + ", \"data\": \"GateWay\"}";
        return JSONObject.stringToValue(result);
    }

    private boolean isBlank(CharSequence cs) {
        int strLen = length(cs);
        if (strLen == 0) {
            return true;
        } else {
            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }

            return true;
        }
    }

    private int length(CharSequence cs) {
        return cs == null ? 0 : cs.length();
    }
}
