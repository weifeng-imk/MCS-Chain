package com.example.mcs;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;


class UIPF
{
    public void BidGeneration(Node_t node_input, Bid_t bid_input) throws UnknownHostException, IOException, NoSuchAlgorithmException
    {
        bid_input.req_type = 4;
        //Record to location
        int index = -1;
        for(int loop = 0; loop < node_input.task_list_curr.size(); loop ++)
        {
            if(node_input.task_list_curr.get(loop).task_id.value == bid_input.task_id.value)
            {
                index = loop;
                break;
            }
        }

        Req_t req = new Req_t(bid_input);

        String msg = req.content
                +req.From.value
                +req.ip_addr
                +req.pk_sender.value.toString()
                +String.valueOf(req.req_type)
                +req.task_id.value
                +String.valueOf(req.trans_type);
        req.Sig_from = BasicCrypto.MySignature1(msg, node_input.node_info.sk, node_input.node_info.pk);
        bid_input.SigFrom = req.Sig_from;

        node_input.bid_list.get(index).add(bid_input);
        node_input.bid_list_record.get(index).add(0);

        //send to others

        BasicTool.BroadcastMsg(req);
    }

  /*  public static void updateRowHeights(JTable table)
    {
        try
        {
            for (int row = 0; row < table.getRowCount(); row++)
            {
                int rowHeight = table.getRowHeight();
                for (int column = 0; column < table.getColumnCount(); column++)
                {
                    Component comp = table.prepareRenderer(table.getCellRenderer(row, column), row, column);
                    rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
                }
                table.setRowHeight(row, rowHeight);
            }
        }
        catch(ClassCastException e) {}
    }*/
}