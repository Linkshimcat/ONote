package viewUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import model.*;
import service.*;
import ioCSV.*;

public class ListPanel extends JPanel {
    private WrongAnswerManager manager;
    private CsvManager csv;

    private DefaultTableModel model;
    private JTable table;

    public ListPanel(WrongAnswerManager manager, CsvManager csv) {
        this.manager = manager;
        this.csv = csv;
        setLayout(new BorderLayout());

        String[] columns = {"과목", "문제 내용", "틀린 이유", "정답", "복습 단계", "다음 복습일"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoCreateRowSorter(true);      // ★ 열 제목을 누르면 정렬
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton btnDelete = new JButton("삭제");
        JButton btnSave = new JButton("저장");
        btnDelete.addActionListener(e -> deleteSelected());
        btnSave.addActionListener(e -> save());

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        bottom.add(btnDelete);
        bottom.add(btnSave);
        add(bottom, BorderLayout.SOUTH);

        refreshTable();
    }

    public void refreshTable() {
        model.setRowCount(0);

        for (WrongAnswer wa : manager.getAll()) {
            model.addRow(new Object[]{
                    wa.getSubject().getName(),
                    wa.getContent(),
                    wa.getReason(),
                    wa.getAnswer(),
                    wa.getStage() + "단계",
                    wa.getNextReviewDate()
            });
        }
    }

    private void deleteSelected() {
        int viewRow = table.getSelectedRow();
        if (viewRow < 0) {
            JOptionPane.showMessageDialog(this, "삭제할 오답을 선택하세요.");
            return;
        }

        int modelRow = table.convertRowIndexToModel(viewRow);   // ★ 정렬된 화면 → 실제 순서
        WrongAnswer wa = manager.getAll().get(modelRow);

        int choice = JOptionPane.showConfirmDialog(this,
                "이 오답을 삭제할까요?\n" + wa.getContent(),
                "삭제 확인",
                JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            manager.delete(modelRow);
            refreshTable();
        }
    }

    private void save() {
        csv.save(manager.getAll());
        JOptionPane.showMessageDialog(this, "저장했습니다.");
    }
}