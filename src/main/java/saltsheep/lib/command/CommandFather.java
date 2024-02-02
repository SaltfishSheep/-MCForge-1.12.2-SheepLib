package saltsheep.lib.command;

import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import saltsheep.lib.SheepLib;

public abstract class CommandFather extends CommandBase {

    protected List<CommandSon> sons;

    public CommandFather() {
        super();
        this.sons = Lists.newLinkedList();
        this.initCommandSons();
    }

    protected abstract void initCommandSons();

    protected List<CommandSon> getSons() {
        return this.sons;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        this.executeIn(server, sender, args);
    }

    public void executeIn(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        try {
            for (CommandSon sonCommand : this.getSons())
                if (sonCommand.isMatch(server, sender, args)) {
                    String[] usefulArgs = new String[args.length - sonCommand.getFlagLength()];
                    for (int i = sonCommand.getFlagLength(); i < args.length; i++)
                        usefulArgs[i - sonCommand.getFlagLength()] = args[i];
                    sonCommand.execute(server, sender, usefulArgs);
                    return;
                }
            sender.sendMessage(new TextComponentString(this.getUsage(sender)));
        } catch (Exception e) {
            sender.sendMessage(new TextComponentString(this.getUsage(sender)));
            SheepLib.printError(e);
            throw new CommandException("Warning!What happen to your command?", e);
        }
    }

    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        Set<String> withoutRepeat = Sets.newHashSet();
        for (CommandSon son : this.sons) {
            if (son.getTab(args) != null)
                withoutRepeat.add(son.getTab(args));
        }
        return Lists.newLinkedList(withoutRepeat);
    }

}
