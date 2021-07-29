package com.xxx.listener;

import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;

/**
 * @version 1.0
 * @author 11
 * @date 2021/7/29 10:45
 */

/**
1、ApplicationStartingEvent：开始启动，但在除了注册监听器和初始化程序之外的任何处理之前。

2、ApplicationEnvironmentPreparedEvent：spring boot 对应Enviroment已经准备完毕，但此时上下文context还没有创建。

3、ApplicationPreparedEvent：spring boot上下文context创建完成，但此时spring中的bean是没有完全加载完成的。

4、ApplicationStartedEvent：在上下文创建之后但在任何应用程序和命令行参数被调用之前发送

5、ApplicationReadyEvent：所有都准备完成

6、ApplicationFailedEvent：spring boot启动异常时执行事件
*/
public class BootApplicationListener implements ApplicationListener<ApplicationStartingEvent> {
    private static final String APP_THREAD_NAME = "XXX-APP-MAIN";

    @Override
    public void onApplicationEvent(ApplicationStartingEvent event) {
        Thread.currentThread().setName(APP_THREAD_NAME);

    }
}
