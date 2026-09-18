# Snake Garden

A self-contained browser edition of the Java Swing Snake game. The web version has no build step, external dependencies, or network assets: open `index.html` locally or publish the repository with GitHub Pages.

## Files

- `index.html` is the playable GitHub Pages version.
- `SnakeGame.java` is the original Java Swing game and is unchanged.
- `code.txt` contains the provided Java source exactly.

## Run the Java version

1. Install a Java Development Kit (JDK).
2. Open a terminal in this folder.
3. Compile the game:

   ```text
   javac SnakeGame.java
   ```

4. Run it:

   ```text
   java SnakeGame
   ```

The Java version uses a 600 x 600 board, a 25px grid, arrow keys, and Enter to restart after game over.

## Play the web version

Open `index.html` in a modern browser. Use the arrow keys or WASD to steer, Space or the Pause button to pause, and Restart to begin a new run. On a phone or tablet, swipe on the board. Eat the coral food, avoid the walls and your own body, and try to beat the best score stored in that browser.

## Deploy with GitHub Pages

These steps publish the site from the `shanya08-04` GitHub account:

1. Sign in to GitHub as `shanya08-04`.
2. Use the repository `https://github.com/shanya08-04/Java-Project`.
3. In a terminal opened in this folder, initialize and push the repository:

   ```text
   git init
   git add index.html README.md SnakeGame.java code.txt
   git commit -m "Create browser Snake game"
   git branch -M main
   git remote add origin https://github.com/shanya08-04/Java-Project.git
   git push -u origin main
   ```

4. Open `https://github.com/shanya08-04/Java-Project/settings/pages`.
5. Under **Build and deployment**, set **Source** to **Deploy from a branch**.
6. Set the branch to `main` and the folder to `/ (root)`, then select **Save**.
7. Wait for the Pages deployment to finish. The site will be available at:

   `https://shanya08-04.github.io/Java-Project/`

GitHub Pages can take a few minutes to publish after the first push. This project intentionally does not include a workflow file; the branch deployment above is sufficient for a static HTML page.

## Limitations

The browser and Java versions are separate implementations, so their random food positions and score histories do not sync. The browser version stores only its best score in the current browser's local storage. GitHub Pages serves the static game and cannot provide server-side scoreboards.
