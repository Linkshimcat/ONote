package viewUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import model.*;
import service.*;
import ioCSV.*;

public class MainPanel extends JPanel {
    private WrongAnswerManager manager;
    private ReviewScheduler scheduler;
    private CsvManager csv;
    private JLabel countLabel;          // MainFrame 헤더의 개수 라벨

    private DefaultTableModel model;
    private JTable table;
    private ArrayList<WrongAnswer> todayList = new ArrayList<>();   // 표의 행 순서와 1:1

    private JLabel lblSubject = new JLabel("과목: -");
    private JLabel lblReason  = new JLabel("틀린 이유: -");
    private JLabel lblAnswer  = new JLabel("정답: -");
    private JLabel lblStage   = new JLabel("복습 단계: -");

    public MainPanel(WrongAnswerManager manager, ReviewScheduler scheduler,
                     CsvManager csv, JLabel countLabel) {
        this.manager = manager;
        this.scheduler = scheduler;
        this.csv = csv;
        this.countLabel = countLabel;

        setLayout(new BorderLayout());

        // ───── 가운데: 오늘 복습 목록 ─────
        String[] columns = {"과목", "문제 내용", "다음 복습일"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;   // 표에서 직접 수정 못 하게
            }
        };

        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // ───── 오른쪽: 상세 정보 ─────
        add(createDetailPanel(), BorderLayout.EAST);

        // ───── 행을 선택하면 상세 정보 갱신 ─────
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                showDetail();
            }
        });

        refreshTable();
    }

    // 오른쪽 패널 조립
    private JPanel createDetailPanel() {
        JPanel infoPanel = new JPanel(new GridLayout(5, 1, 0, 6));
        infoPanel.add(new JLabel("상세 정보"));
        infoPanel.add(lblSubject);
        infoPanel.add(lblReason);
        infoPanel.add(lblAnswer);
        infoPanel.add(lblStage);

        JButton btnComplete = new JButton("복습 완료");
        JButton btnEdit     = new JButton("수정");
        JButton btnDelete   = new JButton("삭제");
        JButton btnSave     = new JButton("저장");

        btnComplete.addActionListener(e -> completeReview());
        btnEdit.addActionListener(e -> editSelected());
        btnDelete.addActionListener(e -> deleteSelected());
        btnSave.addActionListener(e -> save());

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 8, 8));
        buttonPanel.add(btnComplete);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnSave);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        panel.setPreferredSize(new Dimension(260, 0));
        panel.add(infoPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    // 표를 다시 그린다 (데이터가 바뀔 때마다 호출)
    public void refreshTable() {
        model.setRowCount(0);                                  // 기존 행 모두 비우기
        todayList = scheduler.getTodayList(manager.getAll());

        for (WrongAnswer wa : todayList) {
            model.addRow(new Object[]{
                    wa.getSubject().getName(),
                    wa.getContent(),
                    wa.getNextReviewDate()
            });
        }
        countLabel.setText("오늘 복습할 오답: " + todayList.size() + "개");
        clearDetail();
    }

    private void showDetail() {
        int row = table.getSelectedRow();
        if (row < 0) {
            clearDetail();
            return;
        }
        WrongAnswer wa = todayList.get(row);
        lblSubject.setText("과목: " + wa.getSubject().getName());
        lblReason.setText("틀린 이유: " + wa.getReason());
        lblAnswer.setText("정답: " + wa.getAnswer());
        lblStage.setText("복습 단계: " + wa.getStage() + "단계");
    }

    private void clearDetail() {
        lblSubject.setText("과목: -");
        lblReason.setText("틀린 이유: -");
        lblAnswer.setText("정답: -");
        lblStage.setText("복습 단계: -");
    }

    // 선택된 행의 오답을 돌려준다. 선택 안 했으면 안내 후 null
    private WrongAnswer getSelected() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "목록에서 오답을 먼저 선택하세요.");
            return null;
        }
        return todayList.get(row);
    }

    private void completeReview() {
        WrongAnswer wa = getSelected();
        if (wa == null) return;

        scheduler.completeReview(wa);
        JOptionPane.showMessageDialog(this,
                "복습 완료 처리했습니다.\n다음 복습일: " + wa.getNextReviewDate());
        refreshTable();
    }

    private void editSelected() {
        WrongAnswer wa = getSelected();
        if (wa == null) return;

        String content = JOptionPane.showInputDialog(this, "문제 내용", wa.getContent());
        if (content == null) return;                     // 취소를 누르면 null
        String reason = JOptionPane.showInputDialog(this, "틀린 이유", wa.getReason());
        if (reason == null) return;
        String answer = JOptionPane.showInputDialog(this, "정답", wa.getAnswer());
        if (answer == null) return;

        manager.update(manager.getAll().indexOf(wa), wa.getSubject(), content, reason, answer);
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

    private void save() {
        csv.save(manager.getAll());
        JOptionPane.showMessageDialog(this, "저장했습니다.");
    }
}