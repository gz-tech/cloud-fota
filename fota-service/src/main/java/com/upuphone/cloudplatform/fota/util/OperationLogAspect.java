package com.upuphone.cloudplatform.fota.util;

import com.upuphone.cloudplatform.fota.entity.OperationRecord;
import com.upuphone.cloudplatform.fota.mapper.OperationRecordMapper;
import org.apache.logging.log4j.util.Strings;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author guangzheng.ding
 * @date 2021/11/29 10:12
 */
@Aspect
@Component
public class OperationLogAspect {
    @Autowired
    private OperationRecordMapper operationRecordMapper;

    @Pointcut("@annotation(com.upuphone.cloudplatform.fota.util.OperationLog)")
    public void operationLogPointcut() {

    }

    @AfterReturning("@annotation(operationLog)")
    public void saveOperationLog(JoinPoint joinPoint, OperationLog operationLog) {

        String content = operationLog.content();
        String objNameSpel = operationLog.objName();
        String relationIdSpel = operationLog.relationId();
        String module = operationLog.operationModule();
        Object[] args = joinPoint.getArgs();
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        //获取被拦截方法参数名列表(使用Spring支持类库)
        LocalVariableTableParameterNameDiscoverer localVariableTable = new LocalVariableTableParameterNameDiscoverer();
        String[] paraNameArr = localVariableTable.getParameterNames(method);
        //使用SPEL进行key的解析
        ExpressionParser parser = new SpelExpressionParser();
        //SPEL上下文
        StandardEvaluationContext context = new StandardEvaluationContext();
        //把方法参数放入SPEL上下文中
        for(int i=0;i<paraNameArr.length;i++) {
            context.setVariable(paraNameArr[i], args[i]);
        }
        String objName = null;
        // 使用变量方式传入业务动态数据

        String content1 = content;
        if (Strings.isNotBlank(objNameSpel)) {
            objName = parser.parseExpression(objNameSpel).getValue(context, String.class);
            content1 = content + objName;
        }
        String relationId = parser.parseExpression(relationIdSpel).getValue(context, String.class);
        OperationRecord operationRecord = new OperationRecord();
        operationRecord.setOperationId(SnowFlakeUtil.getSnowFlakeId());
        operationRecord.setModule(module);
        operationRecord.setRelationId(relationId);
        operationRecord.setContent(content1);
        operationRecordMapper.insert(operationRecord);
        /*Object[] args = joinPoint.getArgs();
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);

        OperationRecord operationRecord = new OperationRecord();

        operationRecord.setId(SnowFlakeUtil.getSnowFlakeId());
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        OperationLog operationLog = method.getAnnotation(OperationLog.class);
        if (operationLog != null) {
            String operationModule = operationLog.operationModule();
            String operationType = operationLog.operationType();
            String operationDescription = operationLog.operationDescription();
            operationRecord.setTypeName(operationDescription);
            operationRecord.setModule(operationModule);
            operationRecord.setType(operationType);
        }

        final StringBuilder params = new StringBuilder();
        if (args != null && args.length > 0) {
            int i = 0;
            for (Object param : args) {
                params.append(String.format(" arg[%s]=%s", i++, JsonUtility.toJson(param)));
            }
        }


        operationRecord.setRequestParam(params.toString());

        operationRecord.setUserId("admin");
        operationRecord.setUserName("admin");
        operationRecord.setTime(LocalDateTime.now());
        if (args != null && args.length > 0) {
            if (args[0] instanceof TestReleaseAddReqVO) {
                TestReleaseAddReqVO v = (TestReleaseAddReqVO)args[0];
                String relationId = v.getVersionId();
                operationRecord.setRelationId(relationId);
            } else if (args[0] instanceof VersionBO) {
                VersionBO v = (VersionBO)args[0];
                operationRecord.setRelationId(v.getVersionId());
            }

        }


        operationRecordMapper.addOperationRecord(operationRecord);*/

    }

    Map<String, String> convertMap(Map<String, String[]> paramMap) {
        Map<String, String> requestMap = new HashMap<>();
        for (String key:
             paramMap.keySet()) {
            requestMap.put(key, paramMap.get(key)[0]);
        }
        return requestMap;
    }

}
