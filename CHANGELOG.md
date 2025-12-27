# ChangeLog

### Beta_0.4.0_20251227_build_A

#### 功能构建

- (无)

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Beta_0.3.2_20221115_build_A

#### 功能构建

- 依赖升级。
  - 兼容性替换 `dom4j:dom4j:1.6.1` 为 `org.dom4j:dom4j:2.1.3`。

#### Bug修复

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

#### 功能移除

- (无)

---

### Beta_0.3.1_20220903_build_A

#### 功能构建

- 插件升级。
  - 升级 `maven-deploy-plugin` 插件版本为 `2.8.2`。

#### Bug修复

- (无)

#### 功能移除

- (无)

---

### Beta_0.3.0_20220622_build_A

#### 功能构建

- 修改观察器类错误的拼写，该变更是非兼容性变更。
- 修改计划类错误的拼写，该变更是非兼容性变更。
- 优化 MappingTableModel，使其能应对更多的场景。
- 依赖升级。
  - 升级 `junit` 依赖版本为 `4.13.2 `以规避漏洞。
- 变更日志样式更新，老变更日志重命名为 `CHANGELOG.old`。

#### Bug修复

- (无)

#### 功能移除

- (无)
