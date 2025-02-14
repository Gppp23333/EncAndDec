<div align="center">
<h1 align="center">EncAndDec</h1>

<p>使用Burpsuite新发MontoyaApi编写的常见加密算法加解密插件，实现了大体的框架，目前仅支持AES对称加密算法，菜6思路瓶颈，未完待续...</p>

注意：因为某些原因如填充方式等普通Java默认不支持，运行时引入其他加密的第三方库，因此建议使用非商业版的免费OpenJDK，不会去校验，以下是其下载地址。

[Java Platform, Standard Edition 17 Reference Implementations](https://jdk.java.net/java-se-ri/17-MR1)

</div>

> [!WARNING]
> 该插件仅供计算机安全从业人员及相关技术人员进行授权的安全测试和安全审计工作使用。使用本工具进行任何未经授权的网络攻击或渗透测试等行为均属违法，使用者需自行承担相应的法律责任。

![image-20250214222142830](C:\Users\Gppp\AppData\Roaming\Typora\typora-user-images\image-20250214222142830.png)

## 当前特性

- 识别对应的加解密算法及参数后，对需要加密或解密内容进行配置参数并加解密，方便安全人员识别或修改传输的内容。

![image-20250214222432148](C:\Users\Gppp\AppData\Roaming\Typora\typora-user-images\image-20250214222432148.png)

![image-20250214222941909](C:\Users\Gppp\AppData\Roaming\Typora\typora-user-images\image-20250214222941909.png)

- 将对应的加解密算法注册至Intruder模块执行相应的爆破等。

  ![image-20250214223247167](C:\Users\Gppp\AppData\Roaming\Typora\typora-user-images\image-20250214223247167.png)

  ![image-20250214223353362](C:\Users\Gppp\AppData\Roaming\Typora\typora-user-images\image-20250214223353362.png)

  ![image-20250214223505403](C:\Users\Gppp\AppData\Roaming\Typora\typora-user-images\image-20250214223505403.png)

  ![image-20250214223528922](C:\Users\Gppp\AppData\Roaming\Typora\typora-user-images\image-20250214223528922.png)

  ![image-20250214223643470](C:\Users\Gppp\AppData\Roaming\Typora\typora-user-images\image-20250214223643470.png)

## [Gppp23333/EncAndDec](https://github.com/Gppp23333/EncAndDec)

### 菜6所做，师傅们越多就可以产生更多的思路，望谅解orz。
