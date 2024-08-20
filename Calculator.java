import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Stack;

/*
 * @version 2024-08-17
 */

public class Calculator{
	public static void main(String[] args) {
		EventQueue.invokeLater(()-> {
			ButtonFrame frame = new ButtonFrame();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);	
			frame.setResizable(false);
			frame.setLocation(600, 200);
			frame.setTitle("Calculator");
			
			ImageIcon icon = new ImageIcon("C:\\Users\\HP\\Downloads\\calculator.png");
			frame.setIconImage(icon.getImage());
			
		});
	}
}

class ButtonFrame extends JFrame{
	private JPanel buttonPanel, textPanel;
	private JLabel textField;
	private String lastButton = "", res;
	private double result = 0;
	private static final int DEFAULT_WIDTH = 300;
	private static final int DEFAULT_HEIGHT = 350;
	
	public ButtonFrame(){
		setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(5,4));
		buttonPanel.setBackground(Color.PINK);
		buttonPanel.setPreferredSize(new Dimension(300, 230));
		add(buttonPanel, BorderLayout.SOUTH);

		makeButton("AC", new Color(237, 140, 135));
		makeButton("%",  new Color(240, 178, 175));
		makeButton("~", new Color(237, 140, 135));
		makeButton("/",  new Color(240, 178, 175));
		makeButton("7", new Color(240, 178, 175));
		makeButton("8", new Color(240, 178, 175));
		makeButton("9", new Color(240, 178, 175));
		makeButton("x",  new Color(240, 178, 175));
		makeButton("4", new Color(240, 178, 175));
		makeButton("5", new Color(240, 178, 175));
		makeButton("6", new Color(240, 178, 175));
		makeButton("-",  new Color(240, 178, 175));
		makeButton("1", new Color(240, 178, 175));
		makeButton("2", new Color(240, 178, 175));
		makeButton("3", new Color(240, 178, 175));
		makeButton("+",  new Color(240, 178, 175));
		makeButton("00", new Color(240, 178, 175));
		makeButton("0", new Color(240, 178, 175));
		makeButton(".", new Color(240, 178, 175));
		makeButton("=", new Color(240, 178, 175));
		
		textPanel = new JPanel();
		textPanel.setLayout(new BorderLayout());
		
		textField = new JLabel("0");
		textField.setFont(new Font("Arial", Font.PLAIN, 25));
		textField.setBackground(new Color(252, 224, 222));
		textField.setHorizontalAlignment(JLabel.RIGHT);
		textField.setVerticalAlignment(JLabel.TOP);
		
		textPanel.add(textField, BorderLayout.EAST);
		textPanel.setBackground(new Color(250, 207, 202));
		textPanel.setPreferredSize(new Dimension(300, 83));
		add(textPanel, BorderLayout.NORTH);
		
	}
	
	void makeButton(String name, Color c){
		var button = new JButton(name);
		button.setBackground(c);
		buttonPanel.add(button);
		
		
		button.addActionListener(event -> {
			if("AC".equals(name)) {
				textField.setText("0");
				lastButton = "0";
				result = 0;
				res = "0";
			}
			else if("~".equals(name)){
				if(!textField.getText().equals("0")) {
					textField.setText(textField.getText().substring(0, textField.getText().length()-1));
					if(textField.getText().isEmpty()) {
						textField.setText("0");
						lastButton = "0";
					}
					else {
						lastButton = textField.getText().substring(textField.getText().length()-1);
					}
				}
			}
			else if(isOperator(name)) {
				if (lastButton.equals("=")) {
		            textField.setText(res + name);
		            lastButton = name;
				}
				else if(!isOperator(lastButton)) {
					textField.setText(textField.getText() + name);
					lastButton = name;
				}
				else {
					textField.setText(textField.getText().substring(0, textField.getText().length()-1) + name);
					lastButton = name;
				}
			}
			else if("=".equals(name)) {
				lastButton = name;
				Calculate cal = new Calculate();
				result = cal.evaluate(textField.getText());
				res = Double.toString(result);
				textField.setText(res);
			}
			else{
				if(textField.getText().equals("0") || "=".equals(lastButton)) {
					textField.setText(name);
				}
				else {
					textField.setText(textField.getText() + name);
				}
				lastButton = name;
				
			}
		});
	}
	private boolean isOperator(String name) {
		return "+".equals(name) || "-".equals(name) || "x".equals(name) || "/".equals(name) || "%".equals(name);
	}
	
	class Calculate {

	    public double evaluate(String expression) {
	        Stack<Double> numbers = new Stack<>();
	        Stack<Character> operators = new Stack<>();

	        for (int i = 0; i < expression.length(); i++) {
	            char ch = expression.charAt(i);

	            if (Character.isDigit(ch)) {
	                double num = 0;
	                while (i < expression.length() && Character.isDigit(expression.charAt(i))) {
	                    num = num * 10 + (expression.charAt(i) - '0');
	                    i++;
	                }
	                i--;
	                numbers.push(num);
	            } else if (ch == '+' || ch == '-' || ch == 'x' || ch == '/' || ch == '%') {
	                while (!operators.isEmpty() && precedence(ch) <= precedence(operators.peek())) {
	                    applyOperation(numbers, operators.pop());
	                }
	                operators.push(ch);
	            }
	        }

	        while (!operators.isEmpty()) {
	            applyOperation(numbers, operators.pop());
	        }

	        return numbers.pop();
	    }

	    private static void applyOperation(Stack<Double> numbers, char operator) {
	        double b = numbers.pop();
	        double a = numbers.pop();
	        switch (operator) {
	            case '+':
	                numbers.push(a + b);
	                break;
	            case '-':
	                numbers.push(a - b);
	                break;
	            case 'x':
	                numbers.push(a * b);
	                break;
	            case '/':
	                numbers.push(a / b);
	                break;
	            case '%':
	                numbers.push(a % b);
	                break;
	        }
	    }

	    private static int precedence(char operator) {
	        switch (operator) {
	            case '+':
	            case '-':
	                return 1;
	            case 'x':
	            case '/':
	            case '%':
	                return 2;
	        }
	        return -1;
	    }
	}

}
