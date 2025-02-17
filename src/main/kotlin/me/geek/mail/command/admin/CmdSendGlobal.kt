package me.geek.mail.command.admin

import me.geek.mail.api.mail.MailManage
import me.geek.mail.command.CmdExp
import me.geek.mail.common.template.Template
import me.geek.mail.modules.settings.SetTings
import org.bukkit.command.CommandSender
import taboolib.common.platform.command.subCommand
import taboolib.library.reflex.Reflex.Companion.invokeConstructor

/**
 * 作者: 老廖
 * 时间: 2022/9/8
 *
 **/
object CmdSendGlobal: CmdExp {
    override val command = subCommand {
        dynamic("模板ID") {
            suggestion<CommandSender>(uncheck = true) { _, _ ->
                Template.tempPackMap.map { it.key }
            }
            dynamic("全局模式") {
                suggestion<CommandSender> { _, _ ->
                    listOf("全局玩家")
                }
                execute<CommandSender> { _, context, _ ->
                    val pack = Template.getTempPack(context.args()[1])

                    MailManage.getMailData(pack.type)?.javaClass?.invokeConstructor(arrayOf(
                        SetTings.Console.toString(), pack.title, pack.text,
                        SetTings.Console.toString(), SetTings.Console.toString(), "未提取",
                        pack.additional, System.currentTimeMillis().toString(), "0", pack.itemStacks, pack.command))
                        ?.sendGlobalMail()
                }
            }
        }
    }
}