package me.geek.mail.modules

import me.geek.mail.api.hook.hookPlugin
import me.geek.mail.api.mail.MailSub
import me.geek.mail.common.kether.sub.KetherAPI
import me.geek.mail.modules.settings.SetTings
import java.util.UUID
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.regex.Pattern

/**
 * 作者: 老廖
 * 时间: 2022/7/24
 */
class Mail_Money(
    override val mailID: UUID,
    override var title: String,
    override var text: String,
    override var sender: UUID,
    override var target: UUID,
    override var state: String,
    override val mailType: String,
    override var additional: String,
    override var appendixInfo: String,
    override val senderTime: String,
    override var getTime: String,
    override val permission: String = "mail.exp.money",
) : MailSub() {

    constructor() : this(
        mailID = UUID.fromString("00000000-0000-0000-0000-000000000001"),
        mailType = "金币邮件",
        title = "邮件标题",
        text = "邮件文本",
        sender = UUID.fromString("00000000-0000-0000-0000-000000000001"),
        target = UUID.fromString("00000000-0000-0000-0000-000000000001"),
        state = "未领取",
        appendixInfo = "null",
        additional = "0",
        senderTime = "",
        getTime = ""
    )
    constructor(args: Array<String>) : this(
        mailID = UUID.fromString(args[0]),
        mailType = "金币邮件",
        title = args[1],
        text = args[2],
        sender = UUID.fromString(args[3]),
        target = UUID.fromString(args[4]),
        state = args[5],
        appendixInfo = "",
        additional = "",
        senderTime = args[7],
        getTime = args[8]
    ) {
        appendixInfo = "${add(args[6])} ${SetTings.MONEY_MAIL}"
        additional = add(args[6])
    }



    override fun giveAppendix() {
        hookPlugin.money.depositPlayer(Bukkit.getOfflinePlayer(target), this.additional.toDouble());
    }

    override fun condition(player: Player, appendix: String): Boolean {
        val d = KetherAPI.instantKether(player, "Money hasTake ${add(appendix)}").any as Boolean
        if (!d) player.sendMessage("[!] 你没有足够的金币")
        return d
    }

    private fun add(num1: Any): String {
        val matcher = Pattern.compile("\\d+\\.?\\d?\\d").matcher(num1.toString())
        var var1 = "0.0"
        if (matcher.find()) {
            var1 = matcher.group()
        }
        return var1
    }
}
