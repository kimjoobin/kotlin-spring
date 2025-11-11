// Instagram Clone - Custom JavaScript

// ===== 전역 변수 =====
let isLoading = false;

// ===== DOM 로드 완료 시 실행 =====
document.addEventListener('DOMContentLoaded', function() {
    initializeApp();
});

// ===== 앱 초기화 =====
function initializeApp() {
    setupImageUpload();
    setupFormValidation();
    setupCharacterCount();
    console.log('Instagram Clone 앱이 초기화되었습니다.');
}

// ===== 이미지 업로드 관련 =====
function setupImageUpload() {
    const imageInput = document.getElementById('imageFile');
    const uploadArea = document.querySelector('.upload-area');

    if (imageInput && uploadArea) {
        setupDragAndDrop(uploadArea, imageInput);
    }
}

function previewImage(input) {
    if (input.files && input.files[0]) {
        const file = input.files[0];

        // 파일 유효성 검사
        if (!validateImageFile(file)) {
            input.value = '';
            return;
        }

        const reader = new FileReader();
        reader.onload = function(e) {
            showImagePreview(e.target.result);
        };
        reader.readAsDataURL(file);
    }
}

function validateImageFile(file) {
    // 파일 크기 체크 (10MB)
    if (file.size > 10 * 1024 * 1024) {
        showAlert('파일 크기가 너무 큽니다. 10MB 이하의 파일을 선택해주세요.', 'error');
        return false;
    }

    // 파일 형식 체크
    if (!file.type.startsWith('image/')) {
        showAlert('이미지 파일만 업로드 가능합니다.', 'error');
        return false;
    }

    return true;
}

function showImagePreview(imageSrc) {
    const placeholder = document.getElementById('upload-placeholder');
    const previewContainer = document.getElementById('preview-container');
    const previewImage = document.getElementById('image-preview');

    if (placeholder && previewContainer && previewImage) {
        previewImage.src = imageSrc;
        placeholder.style.display = 'none';
        previewContainer.style.display = 'block';
        previewContainer.classList.add('fade-in');
    }
}

function clearImagePreview() {
    const imageInput = document.getElementById('imageFile');
    const placeholder = document.getElementById('upload-placeholder');
    const previewContainer = document.getElementById('preview-container');

    if (imageInput && placeholder && previewContainer) {
        imageInput.value = '';
        placeholder.style.display = 'block';
        previewContainer.style.display = 'none';
    }
}

// ===== 드래그 앤 드롭 =====
function setupDragAndDrop(dropArea, fileInput) {
    ['dragenter', 'dragover', 'dragleave', 'drop'].forEach(eventName => {
        dropArea.addEventListener(eventName, preventDefaults, false);
    });

    function preventDefaults(e) {
        e.preventDefault();
        e.stopPropagation();
    }

    ['dragenter', 'dragover'].forEach(eventName => {
        dropArea.addEventListener(eventName, () => dropArea.classList.add('dragover'), false);
    });

    ['dragleave', 'drop'].forEach(eventName => {
        dropArea.addEventListener(eventName, () => dropArea.classList.remove('dragover'), false);
    });

    dropArea.addEventListener('drop', function(e) {
        const dt = e.dataTransfer;
        const files = dt.files;

        if (files.length > 0) {
            fileInput.files = files;
            previewImage(fileInput);
        }
    }, false);
}

// ===== 폼 유효성 검사 =====
function setupFormValidation() {
    const forms = document.querySelectorAll('form');
    forms.forEach(form => {
        form.addEventListener('submit', function(e) {
            if (!validateForm(this)) {
                e.preventDefault();
            }
        });
    });
}

function validateForm(form) {
    const formType = form.getAttribute('data-form-type') || 'default';

    switch (formType) {
        case 'post':
            return validatePostForm(form);
        case 'login':
            return validateLoginForm(form);
        case 'signup':
            return validateSignupForm(form);
        default:
            return true;
    }
}

function validatePostForm(form) {
    const caption = form.querySelector('#caption')?.value.trim();
    const imageFile = form.querySelector('#imageFile')?.files[0];

    if (!caption && !imageFile) {
        showAlert('내용을 입력하거나 이미지를 선택해주세요.', 'error');
        return false;
    }

    if (caption && caption.length > 500) {
        showAlert('내용은 500자 이하로 입력해주세요.', 'error');
        return false;
    }

    return true;
}

function validateLoginForm(form) {
    const username = form.querySelector('[name="username"]')?.value.trim();
    const password = form.querySelector('[name="password"]')?.value.trim();

    if (!username || !password) {
        showAlert('사용자 이름과 비밀번호를 모두 입력해주세요.', 'error');
        return false;
    }

    return true;
}

function validateSignupForm(form) {
    // 회원가입 폼 유효성 검사는 별도로 처리 (복잡함)
    return true;
}

// ===== 글자 수 세기 =====
function setupCharacterCount() {
    const textareas = document.querySelectorAll('textarea[data-max-length]');
    textareas.forEach(textarea => {
        const maxLength = parseInt(textarea.dataset.maxLength) || 500;
        const countElement = document.querySelector(`#${textarea.id}-count`);

        if (countElement) {
            textarea.addEventListener('input', function() {
                updateCharacterCount(this, countElement, maxLength);
            });
        }
    });

    // 기본 caption 처리
    const captionTextarea = document.getElementById('caption');
    const charCountElement = document.getElementById('char-count');

    if (captionTextarea && charCountElement) {
        captionTextarea.addEventListener('input', function() {
            updateCharacterCount(this, charCountElement, 500);
        });
    }
}

function updateCharacterCount(textarea, countElement, maxLength) {
    const currentLength = textarea.value.length;
    countElement.textContent = currentLength;

    if (currentLength > maxLength) {
        countElement.style.color = '#ed4956';
        textarea.classList.add('is-invalid');
    } else {
        countElement.style.color = 'var(--instagram-gray)';
        textarea.classList.remove('is-invalid');
    }
}

// ===== 알림 시스템 =====
function showAlert(message, type = 'info') {
    const alertContainer = getOrCreateAlertContainer();
    const alertDiv = createAlertElement(message, type);

    alertContainer.appendChild(alertDiv);

    // 3초 후 자동 제거
    setTimeout(() => {
        alertDiv.remove();
    }, 3000);
}

function getOrCreateAlertContainer() {
    let container = document.getElementById('alert-container');
    if (!container) {
        container = document.createElement('div');
        container.id = 'alert-container';
        container.style.cssText = `
            position: fixed;
            top: 20px;
            right: 20px;
            z-index: 9999;
        `;
        document.body.appendChild(container);
    }
    return container;
}

function createAlertElement(message, type) {
    const alertDiv = document.createElement('div');
    alertDiv.className = `alert alert-${type} fade-in`;
    alertDiv.style.cssText = `
        margin-bottom: 10px;
        padding: 12px 16px;
        border-radius: 8px;
        font-size: 14px;
        max-width: 300px;
        box-shadow: 0 2px 10px rgba(0,0,0,0.1);
    `;

    const colors = {
        'error': { bg: '#ffeef0', text: '#c03e3e', border: '#ffccd2' },
        'success': { bg: '#d4edda', text: '#155724', border: '#c3e6cb' },
        'info': { bg: '#d1ecf1', text: '#0c5460', border: '#bee5eb' }
    };

    const color = colors[type] || colors['info'];
    alertDiv.style.backgroundColor = color.bg;
    alertDiv.style.color = color.text;
    alertDiv.style.border = `1px solid ${color.border}`;
    alertDiv.textContent = message;

    return alertDiv;
}

// ===== 로딩 상태 관리 =====
function showLoading(element) {
    if (element) {
        element.disabled = true;
        const originalText = element.textContent;
        element.setAttribute('data-original-text', originalText);
        element.innerHTML = '<i class="fas fa-spinner spin"></i> 로딩 중...';
    }
    isLoading = true;
}

function hideLoading(element) {
    if (element) {
        element.disabled = false;
        const originalText = element.getAttribute('data-original-text');
        if (originalText) {
            element.textContent = originalText;
        }
    }
    isLoading = false;
}

// ===== AJAX 유틸리티 =====
async function makeRequest(url, options = {}) {
    try {
        const response = await fetch(url, {
            headers: {
                'Content-Type': 'application/json',
                ...options.headers
            },
            ...options
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        return await response.json();
    } catch (error) {
        console.error('Request failed:', error);
        showAlert('요청 처리 중 오류가 발생했습니다.', 'error');
        throw error;
    }
}

// ===== 유틸리티 함수들 =====
function debounce(func, wait) {
    let timeout;
    return function executedFunction(...args) {
        const later = () => {
            clearTimeout(timeout);
            func(...args);
        };
        clearTimeout(timeout);
        timeout = setTimeout(later, wait);
    };
}

function formatNumber(num) {
    if (num >= 1000000) {
        return (num / 1000000).toFixed(1).replace(/\.0$/, '') + 'M';
    }
    if (num >= 1000) {
        return (num / 1000).toFixed(1).replace(/\.0$/, '') + 'K';
    }
    return num.toString();
}

function timeAgo(dateString) {
    const now = new Date();
    const past = new Date(dateString);
    const diffInSeconds = Math.floor((now - past) / 1000);

    const intervals = {
        년: 31536000,
        개월: 2592000,
        주: 604800,
        일: 86400,
        시간: 3600,
        분: 60
    };

    for (let [unit, seconds] of Object.entries(intervals)) {
        const interval = Math.floor(diffInSeconds / seconds);
        if (interval >= 1) {
            return `${interval}${unit} 전`;
        }
    }

    return '방금 전';
}

// ===== 전역 함수로 노출 =====
window.previewImage = previewImage;
window.clearImagePreview = clearImagePreview;
window.showAlert = showAlert;
window.showLoading = showLoading;
window.hideLoading = hideLoading;