package viewUI;

import javax.swing.*;
import java.awt.*;
import model.*;
import service.*;

public class RegisterPanel extends JPanel {
    private WrongAnswerManager manager;

    private JComboBox<Subject> subjectBox;
    private JComboBox<String> reasonBox = new JComboBox<>();
    private JTextField contentField = new JTextField();
    private JTextField answerField = new JTextField();

    public RegisterPanel(WrongAnswerManager manager) {
        this.manager = manager;
        setLayout(new BorderLayout());

        Subject[] subjects = {
                new KoreanSubject(),
                new EnglishSubject(),
                new MathSubject(),
                new ProgrammingSubject(),
                new HistorySubject()
        };
        subjectBox = new JComboBox<>(subjects);

        // 과목이 바뀌면 틀린 이유 목록을 다시 채운다
        subjectBox.addActionListener(e -> updateReasons());
        updateReasons();   // 처음 한 번 채워두기

        JButton btnRegister = new JButton("등록");
        btnRegister.addActionListener(e -> register());

        JPanel form = new JPanel(new GridLayout(5, 2, 12, 12));
        form.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        form.add(new JLabel("과목"));        form.add(subjectBox);
        form.add(new JLabel("문제 내용"));    form.add(contentField);
        form.add(new JLabel("틀린 이유"));    form.add(reasonBox);
        form.add(new JLabel("정답"));        form.add(answerField);
        form.add(new JLabel(""));           form.add(btnRegister);

        add(form, BorderLayout.NORTH);
    }

    // 선택된 과목의 틀린 이유로 콤보를 채운다

    private void showInfo(String message) {
        JOptionPane pane = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = pane.createDialog(this, "알림");
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
    }

    private void updateReasons() {
        Subject selected = (Subject) subjectBox.getSelectedItem();
        reasonBox.removeAllItems();

        for (String reason : selected.getWrongReasons()) {
            reasonBox.addItem(reason);
        }
    }

    private void register() {
        String content = contentField.getText().trim();
        String answer = answerField.getText().trim();
        if (content.isEmpty() || answer.isEmpty()) {
            showInfo("문제 내용과 정답을 입력하세요.");
            return;
        }

        Subject subject = (Subject) subjectBox.getSelectedItem();
        String reason = (String) reasonBox.getSelectedItem();

        manager.add(new WrongAnswer(subject, content, reason, answer));

        contentField.setText("");
        answerField.setText("");
        showInfo("등록되었습니다.");
    }
}