package viewUI;

import javax.swing.*;
import java.awt.*;

public class MainFrame {
    private CardLayout cardLayout = new CardLayout();
    private JPanel cardPanel = new JPanel(cardLayout);

    public MainFrame() {
        JFrame frame = new JFrame("ONote");
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        cardPanel.add(new MainPanel(), "메인");
        cardPanel.add(new RegisterPanel(), "등록");
        cardPanel.add(new ReviewPanel(), "복습");
        cardPanel.add(new ListPanel(), "목록");
        cardPanel.add(new SearchPanel(), "검색");

        JButton btnMain = new JButton("메인");
        JButton btnRegister = new JButton("등록");
        JButton btnReview = new JButton("복습");
        JButton btnList = new JButton("목록");
        JButton btnSearch = new JButton("검색");
        btnMain.addActionListener(e -> cardLayout.show(cardPanel, "메인"));
        btnRegister.addActionListener(e -> cardLayout.show(cardPanel, "등록"));
        btnReview.addActionListener(e -> cardLayout.show(cardPanel, "복습"));
        btnList.addActionListener(e -> cardLayout.show(cardPanel, "목록"));
        btnSearch.addActionListener(e -> cardLayout.show(cardPanel, "검색"));

        JPanel menuPanel = new JPanel(new GridLayout(5, 1, 0, 8));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        menuPanel.add(btnMain);
        menuPanel.add(btnRegister);
        menuPanel.add(btnReview);
        menuPanel.add(btnList);
        menuPanel.add(btnSearch);

        frame.add(menuPanel, BorderLayout.WEST);
        frame.add(cardPanel, BorderLayout.CENTER);

        frame.setVisible(true);

    }
}
