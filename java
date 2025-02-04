let shapes = [];

function setup() {
    createCanvas(windowWidth, windowHeight);
    noStroke();
}

function draw() {
    background(0);

    // Display and update all shapes
    for (let i = shapes.length - 1; i >= 0; i--) {
        shapes[i].display();
        shapes[i].update();

        // Remove shapes that are too small
        if (shapes[i].size <= 0) {
            shapes.splice(i, 1);
        }
    }

    // Add new shapes periodically
    if (frameCount % 30 === 0) {  // Every 30 frames (~1 second)
        let newShape = new Shape(random(width), random(height), random(20, 50));
        shapes.push(newShape);
    }
}

function mousePressed() {
    // Destroy shapes when the user clicks/touches the screen
    for (let i = shapes.length - 1; i >= 0; i--) {
        if (shapes[i].contains(mouseX, mouseY)) {
            // Explode the shape into smaller pieces
            let explosion = shapes[i].explode();
            shapes.splice(i, 1);  // Remove the original shape
            shapes.push(...explosion);  // Add the exploded pieces
        }
    }
}

// Shape class
class Shape {
    constructor(x, y, size) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color(random(255), random(255), random(255));
        this.velocity = createVector(random(-2, 2), random(-2, 2));
    }

    display() {
        fill(this.color);
        ellipse(this.x, this.y, this.size, this.size);
    }

    update() {
        this.x += this.velocity.x;
        this.y += this.velocity.y;

        // Bounce off walls
        if (this.x < 0 || this.x > width) this.velocity.x *= -1;
        if (this.y < 0 || this.y > height) this.velocity.y *= -1;

        // Gradually shrink the shape
        this.size -= 0.1;
    }

    contains(px, py) {
        let d = dist(px, py, this.x, this.y);
        return d < this.size / 2;
    }

    explode() {
        let pieces = [];
        for (let i = 0; i < 5; i++) {  // Create 5 smaller pieces
            let pieceSize = this.size / 3;
            let piece = new Shape(this.x, this.y, pieceSize);
            piece.velocity = createVector(random(-5, 5), random(-5, 5));
            pieces.push(piece);
        }
        return pieces;
    }
}
