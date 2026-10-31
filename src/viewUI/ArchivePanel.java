package viewUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import model.*;
import service.*;


public class ArchivePanel extends JPanel {
    private WrongAnswerManager manager;
    private ReviewScheduler scheduler;

    private DefaultTableModel model;
    private JTable table;
    private JLabel statusLabel = new JLabel();
    private ArrayList<WrongAnswer> archivedList = new ArrayList<>(); // 표의 행 순서와 같은 순서

    public ArchivePanel(WrongAnswerManager manager, ReviewScheduler scheduler) {
        this.manager = manager;
        this.scheduler = scheduler;
        setLayout(new BorderLayout());

        statusLabel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        add(statusLabel, BorderLayout.NORTH);

        String[] columns = {"과목", "문항 번호", "문제 내용", "틀린 이유", "정답", "등록일"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton btnRestart = new JButton("다시 복습하기");
        JButton btnDelete = new JButton("삭제");
        btnRestart.addActionListener(e -> restartSelected());
        btnDelete.addActionListener(e -> deleteSelected());

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        bottom.add(btnRestart);
        bottom.add(btnDelete);
        add(bottom, BorderLayout.SOUTH);

        refreshTable();
    }

    public void refreshTable() {
        model.setRowCount(0);
        archivedList.clear();

        for (WrongAnswer wa : manager.getAll()) {
            if (!wa.isArchived()) continue;
            archivedList.add(wa);
            model.addRow(new Object[]{
                    wa.getSubject().getName(),
                    wa.getQNumbers(),
                    wa.getContent(),
                    wa.getReason(),
                    wa.getAnswer(),
                    wa.getCreatedDate()
            });
        }

        if (archivedList.isEmpty()) {
            statusLabel.setText("보관함이 비어 있어요. 모든 복습 단계를 마친 오답이 여기에 모입니다.");
        } else {
            statusLabel.setText("복습을 모두 마친 오답 " + archivedList.size() + "개");
        }
    }

    private WrongAnswer getSelected() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "오답을 먼저 선택하세요.");
            return null;
        }
        return archivedList.get(row);
    }

    private void restartSelected() {
        WrongAnswer wa = getSelected();
        if (wa == null) return;

        scheduler.restart(wa);   // 보관 해제 + 1단계부터 다시
        JOptionPane.showMessageDialog(this,
                "처음부터 다시 복습합니다.\n다음 복습일: " + wa.getNextReviewDate());
        refreshTable();
    }

    private void deleteSelected() {
        WrongAnswer wa = getSelected();
        if (wa == null) return;

        int choice = JOptionPane.showConfirmDialog(this,
                "이 오답을 삭제할까요?\n" + wa.getContent(),
                "삭제 확인",
                JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            manager.delete(manager.getAll().indexOf(wa));
            refreshTable();
        }
    }
}
