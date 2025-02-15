<div align="center">
<h1 align="center">EncAndDec</h1>

<p>使用Burpsuite新发MontoyaApi编写的常见加密算法加解密插件，实现了大体的框架，目前仅支持AES对称加密算法，菜6思路瓶颈，未完待续...</p>

注意：因为某些原因如填充方式等普通Java默认不支持，运行时引入其他加密的第三方库，因此建议使用非商业版的免费OpenJDK，不会去校验，以下是其下载地址。

[Java Platform, Standard Edition 17 Reference Implementations](https://jdk.java.net/java-se-ri/17-MR1)

</div>

> [!WARNING]
> 该插件仅供计算机安全从业人员及相关技术人员进行授权的安全测试和安全审计工作使用。使用本工具进行任何未经授权的网络攻击或渗透测试等行为均属违法，使用者需自行承担相应的法律责任。

![image](https://github.com/user-attachments/assets/fb47c267-d5b1-45b8-be28-aff6efb54a92)


## 当前特性

- 识别对应的加解密算法及参数后，对需要加密或解密内容进行配置参数并加解密，方便安全人员识别或修改传输的内容。

![image](https://github.com/user-attachments/assets/befd7382-dce1-4f9a-9db3-ca306b35f405)
![image](https://github.com/user-attachments/assets/046c048c-003b-4a24-a75a-36d2fd847b0f)


- 将对应的加解密算法注册至Intruder模块执行相应的爆破等。

![image](https://github.com/user-attachments/assets/72066111-2a62-4e36-a1a1-d6f895f28bc9)
![image](https://github.com/user-attachments/assets/28853795-81df-4479-9c57-53e216466cfe)
![image](https://github.com/user-attachments/assets/e4b76a88-9a54-4731-a373-cb15557d710f)
![image](https://github.com/user-attachments/assets/faf3a51d-2680-4de0-b7cd-4dc0f84db826)
![image](https://github.com/user-attachments/assets/fc34d7f3-d492-4726-b536-0778f16f8651)


## [Gppp23333/EncAndDec](https://github.com/Gppp23333/EncAndDec)

### 菜6所做，欢迎师傅们指导和二开，就可以产生更多的思路，望谅解orz。
