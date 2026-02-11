// Cart functionality for PixelCart
let cart = JSON.parse(localStorage.getItem('cart')) || [];

// Add item to cart
function addToCart(product) {
    const existingProduct = cart.find(item => item.id === product.id);

    if (existingProduct) {
        existingProduct.quantity = (existingProduct.quantity || 1) + 1;
    } else {
        cart.push({
            id: product.id,
            name: product.name,
            price: parseFloat(product.price),
            image: product.image,
            category: product.category || 'Unknown',
            quantity: 1
        });
    }

    localStorage.setItem('cart', JSON.stringify(cart));
    updateCartCount();

    // Show success message
    showNotification('Product added to cart!', 'success');
}

// Remove item from cart
function removeFromCart(productId) {
    cart = cart.filter(item => item.id !== productId);
    localStorage.setItem('cart', JSON.stringify(cart));
    updateCartCount();

    // Reload cart page if we're on it
    if (window.location.pathname.includes('cart')) {
        loadCart();
    }
}

// Update quantity
function updateQuantity(productId, newQuantity) {
    const product = cart.find(item => item.id === productId);
    if (product) {
        if (newQuantity <= 0) {
            removeFromCart(productId);
        } else {
            product.quantity = newQuantity;
            localStorage.setItem('cart', JSON.stringify(cart));
            loadCart();
        }
    }
}

// Get cart total
function getCartTotal() {
    return cart.reduce((total, item) => total + (item.price * item.quantity), 0);
}

// Update cart count in navbar
function updateCartCount() {
    const cartCount = cart.reduce((total, item) => total + item.quantity, 0);
    const cartBadge = document.querySelector('.cart-count');
    if (cartBadge) {
        cartBadge.textContent = cartCount;
    }
}

// Load cart on cart page
function loadCart() {
    const cartContainer = document.getElementById('cart-items');
    const cartTotal = document.getElementById('cart-total');

    if (!cartContainer) return;

    if (cart.length === 0) {
        cartContainer.innerHTML = `
            <div class="text-center py-5">
                <i class="fas fa-shopping-cart fa-3x text-muted mb-3"></i>
                <h4 class="text-light">Your cart is empty</h4>
                <p class="text-muted">Add some games to get started!</p>
                <a href="/" class="btn btn-primary mt-3">Continue Shopping</a>
            </div>
        `;
        if (cartTotal) cartTotal.textContent = '₹0';
        return;
    }

    let html = '';
    cart.forEach(item => {
        html += `
            <div class="card bg-dark text-white mb-3 border-secondary">
                <div class="row g-0">
                    <div class="col-md-2">
                        <img src="${item.image}" class="img-fluid rounded-start" alt="${item.name}" style="height: 100%; object-fit: cover;">
                    </div>
                    <div class="col-md-10">
                        <div class="card-body d-flex justify-content-between align-items-center">
                            <div>
                                <h5 class="card-title">${item.name}</h5>
                                <p class="card-text text-muted">${item.category}</p>
                                <p class="card-text"><strong class="text-primary-custom">₹${item.price}</strong></p>
                            </div>
                            <div class="d-flex align-items-center gap-3">
                                <div class="input-group" style="width: 120px;">
                                    <button class="btn btn-outline-secondary" onclick="updateQuantity('${item.id}', ${item.quantity - 1})">-</button>
                                    <input type="text" class="form-control text-center" value="${item.quantity}" readonly>
                                    <button class="btn btn-outline-secondary" onclick="updateQuantity('${item.id}', ${item.quantity + 1})">+</button>
                                </div>
                                <button class="btn btn-danger" onclick="removeFromCart('${item.id}')">
                                    <i class="fas fa-trash"></i>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        `;
    });

    cartContainer.innerHTML = html;
    if (cartTotal) {
        cartTotal.textContent = `₹${getCartTotal().toFixed(2)}`;
    }
}

// Clear cart
function clearCart() {
    cart = [];
    localStorage.setItem('cart', JSON.stringify(cart));
    updateCartCount();
    if (window.location.pathname.includes('cart')) {
        loadCart();
    }
}

// Initialize cart on page load
document.addEventListener('DOMContentLoaded', function () {
    updateCartCount();

    // Load cart if on cart page
    if (window.location.pathname.includes('cart')) {
        loadCart();
    }
});
