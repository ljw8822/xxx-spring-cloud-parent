### **flowable流程引擎**

流程组件可参考：https://blog.csdn.net/qq_18398239/article/details/118197045

### **分布式锁**

分布式锁采用redission实现

### **sentinel-dashboard**

sentinel-dashboard 目前是没有对规则进行持久化的，可以进行改造，下载sentinel-dashboard源码到工程中，
要通过 Sentinel 控制台配置集群流控规则，需要对控制台进行改造。我们提供了相应的接口进行适配。

从 Sentinel 1.4.0 开始，我们抽取出了接口用于向远程配置中心推送规则以及拉取规则：

DynamicRuleProvider<T>: 拉取规则

DynamicRulePublisher<T>: 推送规则

对于集群限流的场景，由于每个集群限流规则都需要唯一的 flowId，因此我们建议所有的规则配置都通过动态规则源进行管理，并在统一的地方生成集群限流规则。
我们提供了新版的流控规则页面，可以针对应用维度推送规则，对于集群限流规则可以自动生成 flowId。用户只需实现 DynamicRuleProvider 和 DynamicRulePublisher 接口，即可实现应用维度推送