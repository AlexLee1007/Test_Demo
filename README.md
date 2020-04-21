# 日常学习内容
 日常框架学习整合,后期会持续加入各种工具类的使用,Kotlin初学担心项目中出现问题后期改动。
## 2020年4月15日
> 初始化上传搭建内容: <br/>
>> 采用MVP + RxJava + Retrofit 结构框架
## 2020年4月16日
> 项目优化: <br/>
>> 1. 添加全局静态引用参数 Constant类 <br/>
>> 2. 添加日志工具Logger整理请求响应前后url / method / Request / Response 内容 <br/>
>> 3. 添加Activity工程栈管理工具 ActivityMassage <br/>
>> 4. 添加整理第三方包引用公共管理文件 config.gradle <br/>
>> 5. 整理文件夹内容
>> 
>>> | 包名 | 内容 |
>>> | :------: | :------: |
>>> | base | MVP的基类 |
>>> | entity | 响应实体类 |
>>> | login | 逻辑代码 |
>>> | retrofit | 网络请求工具 |
>>> | ui | view层页面逻辑代码 |
>>> | utils | 收集封装工具类 |
## 2020年4月21日
> 项目优化:
>> 1. 集成使用持久化项目框架 MMKV 其作用替代SharedPreferences对全局保存变量做持久化存储
>> 2. 集成消息总线 LiveDataEevent 其作用为跨进程消息接收并为BaseView 绑定 LifecycleRegistry 生命监听
>> 3. 集成Room数据库 其属于JetPack的内容,负责 SQLiter 创建管理
>> 4. 添加轮子哥的全局权限申请框架 XXPermissions
>> 5. 添加全局异常收集类CrashHandler 其作用收集APP闪退异常并生成log文件于手机SD卡中方便后期异常收集
>> 6. RxSchedulers文件简化RxJava线程替换操作
