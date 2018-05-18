# QQCompiler

-------------------

> QQ群内自动检测并执行代码的机器人 —— 支持多种语言

> 作者添加了一些额外功能，下有详细目录

-------------------

### 本项目使用/依赖/包含了以下工具

#### [酷Q](https://cqp.cc/forum.php)

#### [JCQ](https://github.com/Sobte/JCQ-CoolQ) by Sobte

#### [MIDITime](https://github.com/cirlabs/miditime) by cirlabs

#### [TiMidity++](https://www.timidity.jp/) by Masanao Izumo

-------------------

<img src="https://github.com/TaihouKai/QQCompiler-Old-Version/blob/master/image/attention.png?raw=true" width="20%">

#### 由于本项目未对代码做过滤，有严重的安全威胁，请谨慎使用 ###

#### 切勿在不信任的群里开放，造成财产损失后果自负 ###

-------------------

### 支持语言目录

* 任何代码的开头都必须是“<触发符号>”

| 语言 | 触发符号 |
| --- | --- |
| [Python](#%E4%BD%BF%E7%94%A8%E6%8C%87%E5%8D%97python%E7%AF%87) | #p; |
| [Java](#%E4%BD%BF%E7%94%A8%E6%8C%87%E5%8D%97java%E7%AF%87) | #j; |
| [CommonLisp](#%E4%BD%BF%E7%94%A8%E6%8C%87%E5%8D%97commonlisp%E7%AF%87) | #l; |

-------------------

### 使用范例

<img src="https://github.com/TaihouKai/QQCompiler-Old-Version/blob/master/image/sample_screenshot.jpg?raw=true" width="100%">

-------------------

### 额外功能目录

| 名称 | 描述 | 触发符号 |
| --- | --- | --- |
| 使用指南 | 详细的使用指南以及功能目录 | #help; |
| 音乐生成指南 | 音乐生成器的使用方法以及范例 | #mhelp; |
| 音乐生成器 | 根据输入的音符信息输出音频 | #m; |

-------------------

### 使用指南：Python篇

* 使用分隔符号(;)分隔每条命令

例

```python
#p;
print("Hello, Python!");
```

-------------------

### 使用指南：Java篇

* 源代码即可

例

```java
#j;
public class myClass { 
public static void main(String[] args) {
System.out.print("Hello, world!");
}
}
```

-------------------

### 使用指南：CommonLisp篇

* 源代码即可

* 不支持中文

例

```cl
#l;
(format t "Hello, CommonLisp!")
```
