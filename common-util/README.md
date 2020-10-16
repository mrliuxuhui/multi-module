# 常用工具

## 1. 数据聚合
DataAggregator 数据聚合消费
### 使用场景
+ 生产者分散产生数据，但消费者需要批量消费数据
+ 生产者分散产生数据，消费者需要对数据合并

使用示例
1. 
```java
class test{
    public static void main(String[] args){
        // 创建
        DataAggregator<String> dataAggregator = 
        new DataAggregator(100, /*批量消费消息数量*/
            20, /*最大等待时间*/
            /**批量消费操作*/
            list -> System.out.println((StringUtils).join(list, ','))
        );
        // 启动消费线程
        dataAggregator.start();

        // 生产线程
        Runnable worker = () -> {
            while (true){
                dataAggregator.put("a");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        List<Thread> threadList = new ArrayList();
        for(int i=0;i<10;i++){
            Thread t = new Thread(worker);
            threadList.add(t);
            t.start();
        }
        
        int i = 0;
        while (true) {
            System.out.println(i++);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
```
2. springboot 整合
```java
import com.flyingwillow.common.util.aggregator.DataAggregator;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.context.annotation.Bean;import org.springframework.context.annotation.Configuration;import org.springframework.stereotype.Service;
@Configuration
class DataAggregateConfig{
    @Bean
    public DataAggregator<XX> dataAggregator(){
        // 创建
        DataAggregator<String> dataAggregator = 
        new DataAggregator(100, /*批量消费消息数量*/
            20, /*最大等待时间*/
            /**批量消费操作*/
            list -> System.out.println((StringUtils).join(list, ','))
        );
        // 启动消费线程
        dataAggregator.start();
        return dataAggregator;
    }   
}

// 使用
@Service
class SomeService{
    @Autowired
    private DataAggregator<XX> dataAggregator;
    
    public void someMethod(){
        dataAggregator.put(a);
    }

}
```

## 2. 条件分支简化工具类
BranchBuilder  条件分支构建工具类

### 使用场景
+ 众多 if/else 增加代码复杂度时

+ 使用示例

```java
class UserCase {
  
    public void old(){
        if(3>5){
            System.out.println("a");
        } else if( 6>5) {
            System.out.println("b");
        }
    }

    public void better() {
        BranchBuilder.nullReturnBuilder()
                        .on(() -> 3>5)
                        .then(() -> System.out.println("a");)
                        .on(() -> 6>5)
                        .then(() -> System.out.println("b");)
                        .apply();
    }
  
    // 带返回值
    public int better() {
        return BranchBuilder.nullReturnBuilder()
                        .on(() -> 3>5)
                        .then(() -> 1)
                        .on(() -> 6>5)
                        .then(() -> 2)
                        .get().get();
    }
}
```