class Stack:
    def __init__(self, size):
        self.max_size = size
        self.stack_array = []
        self.top = -1
    
    def push(self, item):
        self.stack_array.append(item)
        self.top += 1
        print(f"Push: {item}")
    
    def pop(self):
        if not self.is_empty():
            item = self.stack_array.pop()
            self.top -= 1
            print(f"Pop: {item}")
            return item
        return None
    
    def peek(self):
        if not self.is_empty():
            return self.stack_array[-1]
        return None
    
    def is_empty(self):
        return len(self.stack_array) == 0
    
    def is_full(self):
        return len(self.stack_array) == self.max_size
    
    def display_stack(self, message):
        print(f"{message} {self.stack_array}")

class InfixEvaluator:
    
    @staticmethod
    def precedence(operator):
        """Menentukan prioritas operator"""
        precedence_map = {
            '+': 1, '-': 1,
            '*': 2, '/': 2,
            '^': 3
        }
        return precedence_map.get(operator, -1)
    
    @staticmethod
    def is_operator(token):
        """Mengecek apakah token adalah operator"""
        return token in ['+', '-', '*', '/', '^']
    
    @staticmethod
    def is_numeric(token):
        """Mengecek apakah token adalah angka"""
        try:
            float(token)
            return True
        except ValueError:
            return False
    
    @classmethod
    def infix_to_postfix(cls, expression):
        """Konversi infix ke postfix"""
        print("\n=== KONVERSI INFIX KE POSTFIX ===")
        tokens = expression.split()
        stack = Stack(len(tokens))
        postfix = []
        
        for token in tokens:
            if cls.is_numeric(token):
                # Jika token adalah operand, tambahkan ke postfix
                postfix.append(token)
                print(f"Operand: {token} -> langsung ke output")
            
            elif token == '(':
                stack.push(token)
                stack.display_stack("Stack setelah push '('")
            
            elif token == ')':
                # Pop sampai menemukan '('
                while not stack.is_empty() and stack.peek() != '(':
                    postfix.append(stack.pop())
                    stack.display_stack("Stack setelah pop operator")
                
                if not stack.is_empty() and stack.peek() == '(':
                    stack.pop()  # Pop '('
                    print("Pop: ( (dibuang)")
                
                stack.display_stack("Stack setelah memproses )")
            
            elif cls.is_operator(token):
                # Untuk operator
                while (not stack.is_empty() and stack.peek() != '(' and 
                       cls.precedence(token) <= cls.precedence(stack.peek())):
                    postfix.append(stack.pop())
                    stack.display_stack("Stack setelah pop operator dengan prioritas lebih tinggi/sama")
                
                stack.push(token)
                stack.display_stack(f"Stack setelah push operator {token}")
        
        # Pop semua sisa operator
        while not stack.is_empty():
            postfix.append(stack.pop())
            stack.display_stack("Stack setelah pop sisa operator")
        
        return ' '.join(postfix)
    
    @classmethod
    def evaluate_postfix(cls, postfix):
        """Evaluasi postfix expression"""
        print("\n=== EVALUASI POSTFIX ===")
        tokens = postfix.split()
        stack = Stack(len(tokens))
        
        for token in tokens:
            if cls.is_numeric(token):
                # Push operand ke stack
                stack.push(token)
                stack.display_stack(f"Stack setelah push operand {token}")
            
            elif cls.is_operator(token):
                # Pop dua operand
                operand2 = float(stack.pop())
                operand1 = float(stack.pop())
                
                print(f"Menghitung: {operand1} {token} {operand2}")
                
                if token == '+':
                    result = operand1 + operand2
                elif token == '-':
                    result = operand1 - operand2
                elif token == '*':
                    result = operand1 * operand2
                elif token == '/':
                    if operand2 == 0:
                        raise ValueError("Pembagian dengan nol!")
                    result = operand1 / operand2
                elif token == '^':
                    result = operand1 ** operand2
                
                print(f"Hasil: {result}")
                stack.push(str(result))
                stack.display_stack("Stack setelah push hasil")
        
        # Hasil akhir ada di top stack
        return float(stack.pop())

def main():
    print("PROGRAM EVALUASI EKSPRESI ARITMATIKA")
    print("======================================")
    print("Catatan: Gunakan spasi antar token")
    print("Contoh: ( 3 + 5 ) * 2 - 8 / 4")
    
    infix = input("\nMasukkan ekspresi infix: ")
    
    try:
        # Konversi ke postfix
        postfix = InfixEvaluator.infix_to_postfix(infix)
        print(f"\nHasil Postfix: {postfix}")
        
        # Evaluasi postfix
        result = InfixEvaluator.evaluate_postfix(postfix)
        print("\n======================================")
        print(f"Hasil Akhir: {result}")
        
    except Exception as e:
        print(f"Error: {e}")

if __name__ == "__main__":
    main()