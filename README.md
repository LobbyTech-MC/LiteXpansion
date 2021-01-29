# LiteXpansion
LiteXpansion 是继承了 SlimeXpansion 所有物品和部分功能的扩展, 开发者正在努力开发使其能提供类似 IC2 模组的体验.

## :floppy_disk: 下载
单击此处下载 LiteXpansion: [下载 LiteXpansion](https://github.com/StarWishsama/LiteXpansion-CN/releases)

**注意: 使用 LiteXpansion 前, 你必须安装 Slimefun. 如果你还没有的话可以在这里下载: [Slimefun 汉化版](https://github.com/StarWishsama/Slimefun4).**

<p align="center">
  <a href="https://github.com/StarWishsama/LiteXpansion-CN/actions">
    <img src="https://github.com/StarWishsama/LiteXpansion-CN/workflows/AutoBuild/badge.svg" alt="Github Actions"/>
  </a>
</p>

## 插件内容
### :card_index_dividers: 物品
- 食物合成器 - 用合成的食物填报你的肚子
- 镁钍合金 - 坚如磐石的合金
- 钍 - 一种放射性极强的合金
- 镁钍合金粉 - (制作中, 还没有实装)
- 钍粉 - (制作中, 还没有实装)
- 废料 - 用于合成 UU 物质
- UU 物质 - 可以创造出多种物品
- 铱 - (制作中, 还没有实装)
- 铱板 - (制作中, 还没有实装)
- 精炼铁锭 - 可以用于合成其他物品的金属 (制作中, 还没有实装)
- 机器方块 - 用于制造机器 (制作中, 还没有实装)

### :electric_plug: 机器
- 回收机 - 这台机器可以把所有东西转变为废料
- 物质生成机 - 使用废料创造 UU 物质, "只需要亿点点电"

### :hammer_and_wrench: 工具
- 扳手 - 轻松拆除机器而不用破坏它们 (制作中, 还没有实装)

### :crossed_swords: 武器
- 纳米剑 - 一把高科技的武器, 可以轻松斩断对手

### :shield: 装备
- 电动护甲 - 抵消所有对自身的伤害.

### :brain: UU 物质
UU物质是一种不易制造的稀有物质, 可以用废料在物质生成机合成. 你可以用 UU 物质合成数不胜数的物品, 例如草方块、玻璃、铜锭等.

如果你是服务器的管理员, 你可以自定义 UU 物质可以合成的物品及其合成配方.
#### :gear: 配置
想要添加、删除或者修改 UU 物质可合成物品的合成配方, 你需要修改 `/plugins/LiteXpansion/uumatter.yml` 中的内容.

在 `recipes` 下可以指定合成出的物品, 标准物品名称可以在 [这里](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Material.html) 找到, 你也可以使用 Slimefun 及其扩展插件的物品 ID. 如果你想合成超过一个以上的物品, 你可以在物品名称后加上一个英文冒号 (`:`) 和物品数量. 自定义合成表中, 空格为无物品, `x` 为 UU 物质.

这里有一个合成二十个煤炭的合成表的例子:
```yaml
recipes:
  # 合成出的物品, 在后面添加冒号 (:) 可以指定合成个数
  # 支持标准物品名称 (https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Material.html) 和 Slimefun 物品 ID
  'COAL:20':
    # 合成表, x = UU 物质, space = 无物品
    - '  x'
    - 'x  '
    - '  x'
```

### :computer: Discord
如果你想知道 Slimefun 的物品 ID, 你可以加入我们的 Discord 群组并在 `#bot-spam` 频道输入 `!item <名字/ID>` 并发送.
你也可以在 `#litexpansion` 频道中讨论有关 LiteXpansion 的一切.

<p align="center">
  <a href="https://discord.gg/slimefun">
    <img src="https://discordapp.com/api/guilds/565557184348422174/widget.png?style=banner3" alt="Discord Invite"/>
  </a>
</p>
