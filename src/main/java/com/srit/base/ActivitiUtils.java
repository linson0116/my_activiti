package com.srit.base;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.List;


/**
 * Created by linson on 2017/7/1.
 */
public class ActivitiUtils {
    Logger logger = Logger.getLogger(ActivitiUtils.class);
    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    @Test
    public void myTest() {
//        String resName = "helloworld";//
//        String depName = "hello";
//        Deployment deployment = deploymentProcessDefinition(depName, resName);
//        System.out.println(deployment);

//        deleteProcessDefinition("30001");
        findProcessDefinition();
    }

    /**
     * 部署流程定义
     */
    public Deployment deploymentProcessDefinition(String depName, String resName) {
        Deployment deployment = processEngine.getRepositoryService()//与流程定义和部署对象相关的Service
                .createDeployment()//创建一个部署对象
                .name(depName)//添加部署的名称
                .addClasspathResource("diagrams/" + resName + ".bpmn")//从classpath的资源中加载，一次只能加载一个文件
                .addClasspathResource("diagrams/" + resName + ".png")//从classpath的资源中加载，一次只能加载一个文件
                .deploy();//完成部署
        logger.info("流程已部署 " + deployment);
        return deployment;
    }

    /**
     * 查询流程定义
     */
    public void findProcessDefinition() {
        List<ProcessDefinition> list = processEngine.getRepositoryService()//与流程定义和部署对象相关的Service
                .createProcessDefinitionQuery()//创建一个流程定义的查询
                /**指定查询条件,where条件*/
//						.deploymentId(deploymentId)//使用部署对象ID查询
//						.processDefinitionId(processDefinitionId)//使用流程定义ID查询
//						.processDefinitionKey(processDefinitionKey)//使用流程定义的key查询
//						.processDefinitionNameLike(processDefinitionNameLike)//使用流程定义的名称模糊查询

                /**排序*/
                .orderByProcessDefinitionVersion().asc()//按照版本的升序排列
//						.orderByProcessDefinitionName().desc()//按照流程定义的名称降序排列

                /**返回的结果集*/
                .list();//返回一个集合列表，封装流程定义
//						.singleResult();//返回惟一结果集
//						.count();//返回结果集数量
//						.listPage(firstResult, maxResults);//分页查询
        if (list != null && list.size() > 0) {
            for (ProcessDefinition pd : list) {
                logger.info("部署对象ID：" + pd.getDeploymentId());
                logger.info("流程定义ID:" + pd.getId());//流程定义的key+版本+随机生成数
                logger.info("流程定义的名称:" + pd.getName());//对应helloworld.bpmn文件中的name属性值
                logger.info("流程定义的key:" + pd.getKey());//对应helloworld.bpmn文件中的id属性值
                logger.info("流程定义的版本:" + pd.getVersion());//当流程定义的key值相同的相同下，版本升级，默认1
                logger.info("资源名称bpmn文件:" + pd.getResourceName());
                logger.info("资源名称png文件:" + pd.getDiagramResourceName());
                logger.info("#########################################################");
            }
        }
    }

    /**
     * 删除流程定义
     */
    public void deleteProcessDefinition(String deploymentId) {
        //使用部署ID，完成删除
        /**
         * 不带级联的删除
         *    只能删除没有启动的流程，如果流程启动，就会抛出异常
         */
//		processEngine.getRepositoryService()//
//						.deleteDeployment(deploymentId);

        /**
         * 级联删除
         * 	  不管流程是否启动，都能可以删除
         */
        processEngine.getRepositoryService()//
                .deleteDeployment(deploymentId, true);
        logger.info("流程：" + deploymentId + " 删除成功！");

    }
}
