package viewUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import model.*;
import service.*;

public class ReviewPanel extends JPanel {
    private WrongAnswerManager manager;
    private ReviewScheduler scheduler;
    private JLabel countLabel;

    private DefaultTableModel model;
    private JTable table;
    private ArrayList<WrongAnswer> todayList = new ArrayList<>();
    private JLabel statusLabel = new JLabel();

    public ReviewPanel(WrongAnswerManager manager, ReviewScheduler scheduler, JLabel countLabel) {
        this.manager = manager;
        this.scheduler = scheduler;
        this.countLabel = countLabel;
        setLayout(new BorderLayout());

        statusLabel.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));
        add(statusLabel, BorderLayout.NORTH);

        String[] columns = {"과목", "문제 내용", "틀린 이유", "복습 단계", "다음 복습일"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton btnComplete = new JButton("복습 완료");
        btnComplete.addActionListener(e -> completeReview());

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        bottom.add(btnComplete);
        add(bottom, BorderLayout.SOUTH);

        refreshTable();
    }

    public void refreshTable() {
        model.setRowCount(0);
        todayList = scheduler.getTodayList(manager.getAll());

        for (WrongAnswer wa : todayList) {
            model.addRow(new Object[]{
                    wa.getSubject().getName(),
                    wa.getContent(),
                    wa.getReason(),
                    wa.getStage() + "단계",
                    wa.getNextReviewDate()
            });
        }

        if (todayList.isEmpty()) {
            statusLabel.setText("오늘 복습할 오답이 없습니다.");
        } else {
            statusLabel.setText("오늘 복습할 오답 " + todayList.size() + "개");
        }
        countLabel.setText("오늘 복습할 오답: " + todayList.size() + "개");
    }

    private void completeReview() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "복습할 오답을 선택하세요.");
            return;
        }

        WrongAnswer wa = todayList.get(row);
        scheduler.completeReview(wa);

        JOptionPane.showMessageDialog(this,
                "복습 완료 처리했습니다.\n다음 복습일: " + wa.getNextReviewDate());
        refreshTable();
    }
}