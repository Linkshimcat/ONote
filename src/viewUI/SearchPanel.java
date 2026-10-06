package viewUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import model.*;
import service.*;

public class SearchPanel extends JPanel {
    private WrongAnswerManager manager;
    private JComboBox<String> subjectBox;
    private JTextField keywordField = new JTextField(15);
    private JLabel resultLabel = new JLabel();
    private DefaultTableModel model;
    private JTable table;

    public SearchPanel(WrongAnswerManager manager) {
        this.manager = manager;
        setLayout(new BorderLayout());

        String[] subjects = {"전체", "국어", "영어", "수학", "프로그래밍", "한국사"};
        subjectBox = new JComboBox<>(subjects);

        JButton btnSearch = new JButton("검색");
        btnSearch.addActionListener(e -> search());
        keywordField.addActionListener(e -> search());   // 엔터로도 검색

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 12));
        top.add(new JLabel("검색 기준"));
        top.add(subjectBox);
        top.add(keywordField);
        top.add(btnSearch);
        top.add(resultLabel);
        add(top, BorderLayout.NORTH);

        String[] columns = {"과목", "문제 내용", "틀린 이유", "정답", "다음 복습일"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        table.setAutoCreateRowSorter(true);
        add(new JScrollPane(table), BorderLayout.CENTER);

        search();
    }

    public void search() {
        String subjectName = (String) subjectBox.getSelectedItem();
        String keyword = keywordField.getText().trim();

        ArrayList<WrongAnswer> result = manager.search(subjectName, keyword);

        model.setRowCount(0);
        for (WrongAnswer wa : result) {
            model.addRow(new Object[]{
                    wa.getSubject().getName(),
                    wa.getContent(),
                    wa.getReason(),
                    wa.getAnswer(),
                    wa.getNextReviewDate()
            });
        }

        resultLabel.setText("검색 결과 " + result.size() + "건");
    }
}