const main = document.querySelectorAll('main');
const aside = document.querySelectorAll('aside');
const pass = document.querySelector('.pass');
const confPass = document.querySelector('.confPass');


document.querySelector('.loginBtn').addEventListener('click', () => {
   main.forEach(elem => elem.classList.toggle('active'))
});

document.querySelector('.signupBtn').addEventListener('click', () => {
   aside.forEach(elem => elem.classList.toggle('active'))
});

document.querySelector('.main').addEventListener('click', () => {
   main.forEach(elem => elem.classList.toggle('active'))
});

document.querySelector('.aside').addEventListener('click', () => {
   aside.forEach(elem => elem.classList.toggle('active'))
});

document.querySelector('.signupFormBtn').addEventListener('click', () => {
   if(pass.value === confPass.value){
      alert('User created success ')
   } else {
      alert('You input two different password try again')
   }
});

if (window.location.href === 'http://localhost:8080/?error'){
   document.querySelector('.error').textContent = '*Wrong user or password';
}



