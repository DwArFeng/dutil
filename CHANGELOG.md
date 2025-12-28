# ChangeLog

## Beta_0.4.0_20251227_build_A

### 功能构建

- 更新 README.md。

- Wiki 编写。
  - 构建 wiki 目录结构。
  - docs/wiki/en-US/Contents.md。
  - docs/wiki/en-US/Introduction.md。
  - docs/wiki/zh-CN/Contents.md。
  - docs/wiki/zh-CN/Introduction.md。

- `dutil-develop` 子模块测试类优化。
  - com.dwarfeng.dutil.develop.timer.ListTimerTest。

- `dutil-develop` 子模块类优化，完善文档注释，消除警告。
  - com.dwarfeng.dutil.develop.backgr.AbstractTask。

- `dutil-basic` 子模块类优化，完善文档注释，消除警告。
  - com.dwarfeng.dutil.basic.cls.ClassUtil。
  - com.dwarfeng.dutil.basic.cna.model.ReferenceModel。
  - com.dwarfeng.dutil.basic.cna.model.AbstractListModel。
  - com.dwarfeng.dutil.basic.cna.model.AbstractMapModel。
  - com.dwarfeng.dutil.basic.cna.model.AbstractReferenceModel。
  - com.dwarfeng.dutil.basic.cna.model.AbstractSetModel。
  - com.dwarfeng.dutil.basic.cna.model.DelegateKeyListModel。
  - com.dwarfeng.dutil.basic.cna.model.DelegateKeySetModel。
  - com.dwarfeng.dutil.basic.cna.model.DelegateListModel。
  - com.dwarfeng.dutil.basic.cna.model.DelegateMapModel。
  - com.dwarfeng.dutil.basic.cna.model.DelegateSetModel。
  - com.dwarfeng.dutil.basic.cna.model.MapKeySetModel。
  - com.dwarfeng.dutil.basic.cna.model.ModelUtil。
  - com.dwarfeng.dutil.basic.io.CT。
  - com.dwarfeng.dutil.basic.io.FileUtil。
  - com.dwarfeng.dutil.basic.io.IOUtil。
  - com.dwarfeng.dutil.basic.io.StringInputStream。
  - com.dwarfeng.dutil.basic.mea.TimeMeasurer。
  - com.dwarfeng.dutil.basic.num.NumberUtil。
  - com.dwarfeng.dutil.basic.num.unit.DataSize。
  - com.dwarfeng.dutil.basic.num.unit.Time。
  - com.dwarfeng.dutil.basic.prog.ProcessException。
  - com.dwarfeng.dutil.basic.str.UUIDUtil。

- `dutil-develop` 子模块类废弃。
  - com.dwarfeng.dutil.develop.resource.Url2RepoRresource。

- `dutil-develop` 子模块类新增。
  - com.dwarfeng.dutil.develop.resource.Url2RepoResource。

- 插件升级。
  - 升级 `maven-clean-plugin` 插件版本为 `3.1.0`。

- 依赖升级。
  - 升级 `log4j2` 依赖版本为 `2.25.3` 以规避漏洞。

- 优化文件格式。
  - 优化 `*.properties` 文件的格式。
  - 优化 `*.java` 文件的格式。
  - 优化 `pom.xml` 文件的格式。

- 优化开发环境支持。
  - 在 .gitignore 中添加 VSCode 相关文件的忽略规则。
  - 在 .gitignore 中添加 Cursor IDE 相关文件的忽略规则。

### Bug 修复

- 修复部分类中错误的 javadoc。
  - com.dwarfeng.dutil.basic.cna.ArrayUtil。

- 修复部分类中的 bug。
  - com.dwarfeng.dutil.develop.timer.ListTimer。
  - com.dwarfeng.dutil.develop.i18n.I18nUtil。
  - com.dwarfeng.dutil.develop.resource.ResourceUtil。
  - com.dwarfeng.dutil.basic.cna.ArrayUtil。
  - com.dwarfeng.dutil.basic.cna.model.ModelUtil。
  - com.dwarfeng.dutil.develop.timer.TimerUtil。
  - com.dwarfeng.dutil.basic.cna.IDMap。

### 功能移除

- 移除没有被使用的内部类。
  - com.dwarfeng.dutil.basic.gui.swing.PioltLight。

---

## Beta_0.3.2_20221115_build_A

### 功能构建

- 依赖升级。
  - 兼容性替换 `dom4j:dom4j:1.6.1` 为 `org.dom4j:dom4j:2.1.3`。

### Bug 修复

- 规范所有测试类的类名。

- 修改部分类中不规范的代码。
  - com.dwarfeng.dutil.basic.io.StringOutputStream。
  - com.dwarfeng.dutil.basic.cna.AttributeComplex。
  - com.dwarfeng.dutil.basic.str.StringComparator。
  - com.dwarfeng.dutil.basic.num.unit.Angle。
  - com.dwarfeng.dutil.basic.num.unit.DataSize。
  - com.dwarfeng.dutil.basic.num.unit.Mass。
  - com.dwarfeng.dutil.basic.num.unit.Time。

- 优化 UUIDUtil 中不规范的位移代码。

### 功能移除

- (无)

---

## Beta_0.3.1_20220903_build_A

### 功能构建

- 插件升级。
  - 升级 `maven-deploy-plugin` 插件版本为 `2.8.2`。

### Bug 修复

- (无)

### 功能移除

- (无)

---

## Beta_0.3.0_20220622_build_A

### 功能构建

- 修改观察器类错误的拼写，该变更是非兼容性变更。
- 修改计划类错误的拼写，该变更是非兼容性变更。
- 优化 MappingTableModel，使其能应对更多的场景。
- 依赖升级。
  - 升级 `junit` 依赖版本为 `4.13.2 `以规避漏洞。
- 变更日志样式更新，老变更日志重命名为 `CHANGELOG.old`。

### Bug 修复

- (无)

### 功能移除

- (无)
