import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class TreeSizeCalculator implements Callable<Integer> {
    private Node root;
    private ExecutorService executorService;
    
    public TreeSizeCalculator(Node root, ExecutorService executorService) {
        this.root = root;
        this.executorService = executorService;
    }

    @Override
    public Integer call() throws Exception {
        // Base case: if root is null, size is 0
        if (root == null) {
            return 0;
        }

        // Create tasks for left and right subtrees
        Future<Integer> leftSizeTask = null;
        Future<Integer> rightSizeTask = null;

        // Submit left subtree calculation to executor service
        if (root.getLeft() != null) {
            leftSizeTask = executorService.submit(
                new TreeSizeCalculator(root.getLeft(), executorService)
            );
        }

        // Submit right subtree calculation to executor service
        if (root.getRight() != null) {
            rightSizeTask = executorService.submit(
                new TreeSizeCalculator(root.getRight(), executorService)
            );
        }

        // Get results from both subtrees (wait if necessary)
        int leftSize = (leftSizeTask != null) ? leftSizeTask.get() : 0;
        int rightSize = (rightSizeTask != null) ? rightSizeTask.get() : 0;

        // Total size = current node + left subtree size + right subtree size
        return 1 + leftSize + rightSize;
    }
}