package saltsheep.lib.common;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClipboardHelper {

    @SideOnly(Side.CLIENT)
    public static void writeClip(String clip) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable trandata = null;
        trandata = new StringSelection(clip);
        clipboard.setContents(trandata, null);
    }

    @SideOnly(Side.CLIENT)
    public static String readClip() throws UnsupportedFlavorException, IOException {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable clipT = clipboard.getContents(null);
        if (clipT != null)
            if (clipT.isDataFlavorSupported(DataFlavor.stringFlavor))
                return (String) clipT.getTransferData(DataFlavor.stringFlavor);
        return "";
    }

}
