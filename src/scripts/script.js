/*=====toggle icon navbar===*/
let menuIcon = document.querySelector('#menu-icon');
let navbar = document.querySelector('.navbar');

menuIcon.onclick = () => {
    menuIcon.classList.toggle('bx-x');
    navbar.classList.toggle('active');
};

/*=====section active link=====*/
let sections = document.querySelectorAll('section');
let navlinks = document.querySelectorAll('header nav a');

window.onscroll = () => {
    sections.forEach(sec => {
        let top = window.scrollY;
        let offset = sec.offsetTop - 150;
        let height = sec.offsetHeight;
        let id = sec.getAttribute('id');
        
        if (top >= offset && top < offset + height) {
            navlinks.forEach(links => {
                links.classList.remove('active');
                document.querySelector('header nav a[href*=' + id + ']').classList.add('active');
            });
        }
    });
    
    /*======sticky navbar=====*/
    let header = document.querySelector('header');
    header.classList.toggle('sticky', window.scrollY > 100);
    
    /*==remove toggle icon navbar when click navbar link (scroll)*/
    menuIcon.classList.remove('bx-x');
    navbar.classList.remove('active');

    /*==show/hide back to top icon==*/
    let footerIconTop = document.querySelector('.footer-iconTop');
    if ((window.innerHeight + window.scrollY) >= document.body.offsetHeight) {
        footerIconTop.style.display = 'flex';
    } else {
        footerIconTop.style.display = 'none';
    }
};

/*====scroll reveal=====*/
ScrollReveal({
    reset: true,
    distance: '80px',
    duration: 2000,
    delay: 200
});

ScrollReveal().reveal('.home-content, .heading', { origin: 'top' });
ScrollReveal().reveal('.home-image, .services-container, .portfolio-box, .contact form', { origin: 'bottom' });
ScrollReveal().reveal('.home-content h1, .about-image', { origin: 'left' });
ScrollReveal().reveal('.home-content p, .about-content', { origin: 'right' });

/*====typed js===*/
const typed = new Typed('.multiple-text', {
    strings: ['freelancer', 'digital marketer', 'web developer', 'Software Developer', 'Innovator'],
    typeSpeed: 100,
    backSpeed: 100,
    backDelay: 1000,
    loop: true
});

document.addEventListener('DOMContentLoaded', () => {
  const progressBars = document.querySelectorAll('.progress-bar');
  const testimonials = document.querySelectorAll('.testimonial-box');

  const observerOptions = {
    threshold: 0.5
  };

  const progressBarObserver = new IntersectionObserver((entries, observer) => {
    entries.forEach(entry => {
      if (entry.isIntersecting) {
        const bar = entry.target;
        const progress = bar.getAttribute('data-progress');
        bar.style.width = progress;
        const progressText = bar.nextElementSibling;
        if (progressText && progressText.classList.contains('progress-text')) {
          progressText.style.left = progress;
        }
        observer.unobserve(bar);
      }
    });
  }, observerOptions);

  progressBars.forEach(bar => {
    progressBarObserver.observe(bar);
  });

  const testimonialObserver = new IntersectionObserver((entries, observer) => {
    entries.forEach(entry => {
      if (entry.isIntersecting) {
        entry.target.classList.add('slide-in');
        observer.unobserve(entry.target);
      }
    });
  }, observerOptions);

  testimonials.forEach(testimonial => {
    testimonialObserver.observe(testimonial);
  });
});





