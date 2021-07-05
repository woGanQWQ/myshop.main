package com.zb.util;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.common.utils.AddressUtils;
import com.alibaba.otter.canal.protocol.CanalEntry.*;
import com.alibaba.otter.canal.protocol.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.util.List;

@Component
public class ClientSample {

    @Autowired
    private RestTemplate restTemplate;


    public void main() {
        System.out.println("启动代码同步器");
// 创建链接
        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress(AddressUtils.getHostIp(),
                11111), "example", "", "");
        int batchSize = 1000;
        try {
            connector.connect();
            connector.subscribe(".*\\..*");
            connector.rollback();
            boolean flag = true;
            while (flag) {
                Message message = connector.getWithoutAck(batchSize); // 获取指定数量的数据
                long batchId = message.getId();
                int size = message.getEntries().size();
                if (batchId == -1 || size == 0) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    printEntry(message.getEntries());
                }
                connector.ack(batchId); // 提交确认
            }
        } finally {
            connector.disconnect();
        }
    }


    private void printEntry(List<Entry> entrys) {
        for (Entry entry : entrys) {
            if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN || entry.getEntryType() == EntryType.TRANSACTIONEND) {
                continue;
            }

            RowChange rowChage = null;
            try {
                rowChage = RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(),
                        e);
            }

            EventType eventType = rowChage.getEventType();
            System.out.println(String.format("================> binlog[%s:%s] , name[%s,%s] , eventType : %s",
                    entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                    entry.getHeader().getSchemaName(), entry.getHeader().getTableName(),
                    eventType));

            if (entry.getHeader().getSchemaName().equals("shop_content") && entry.getHeader().getTableName().equals("tb_content")) {
                for (RowData rowData : rowChage.getRowDatasList()) {
                    if (eventType == EventType.DELETE || eventType == EventType.INSERT ) {
                        printColumn(rowData.getBeforeColumnsList());
                    }else {
                        printColumn(rowData.getAfterColumnsList());
                    }
                }
            }
        }
    }

    private void printColumn(List<Column> columns) {
        Integer cid = null;
        for (Column column : columns) {
            System.out.println(column.getName()+":"+column.getValue());
            if (column.getName().equals("category_id")) {
                cid = Integer.parseInt(column.getValue().toString());
                String result = restTemplate.getForObject("http://localhost:9090/web05?cid=" + cid, String.class);
                System.out.println(result);
                break;
            }

        }
    }
}