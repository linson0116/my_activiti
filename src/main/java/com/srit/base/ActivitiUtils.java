package com.srit.base;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.junit.Test;

/**
 * Created by linson on 2017/7/1.
 */
public class ActivitiUtils {
    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    @Test
    public void myTest() {
        String resName = "helloworld";
        String depName = "helloworld入门程序";
        Deployment deployment = deploymentProcessDefinition(depName, resName);
        System.out.println(deployment);//
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
        return deployment;
    }
}
