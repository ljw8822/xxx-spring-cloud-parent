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


### **在线接口文档**

采用swagger2

swagger2常用注解：

@Api：修饰整个类，描述Controller的作用

@ApiOperation：描述一个类的一个方法，或者说一个接口

@ApiParam：单个参数描述

@ApiModel：用对象来接收参数

@ApiProperty：用对象接收参数时，描述对象的一个字段

@ApiResponse：HTTP响应其中1个描述

@ApiResponses：HTTP响应整体描述

@ApiIgnore：使用该注解忽略这个API

@ApiError ：发生错误返回的信息

@ApiImplicitParam：一个请求参数

@ApiImplicitParams：多个请求参数

微服务文档整合通过在gateway中swagger提供的SwaggerResourcesProvider接口，根据gateway提供的路由信息将微服务解析出来，往gateway的资源中添加各个微服务的
swagger文档资源。

1、在各个微服务中配置swagger

2、在gateway中加入一下配置，并引入knife4j包（swagger-bootstrap-ui的升级版），这样就能在同一个页面访问到各个微服务提供的接口文档，而不必多个地之间切换。

    
    @Override
    public List<SwaggerResource> get() {

        List<SwaggerResource> resources = new ArrayList<>();
        List<String> routes = new ArrayList<>();
        //取出gateway的route
        routeLocator.getRoutes().subscribe(route -> routes.add(route.getId()));
        //结合配置的route-路径(Path)，和route过滤，只获取有效的route节点
        gatewayProperties.getRoutes().stream().filter(routeDefinition -> routes.contains(routeDefinition.getId()))
                .forEach(routeDefinition -> routeDefinition.getPredicates().stream()
                        .filter(predicateDefinition -> ("Path").equalsIgnoreCase(predicateDefinition.getName()))
                        .forEach(predicateDefinition -> resources.add(swaggerResource(routeDefinition.getId(),
                                predicateDefinition.getArgs().get(NameUtils.GENERATED_NAME_PREFIX + "0")
                                        .replace("/**", API_URI)))));
        return resources;
    }
    
    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }



