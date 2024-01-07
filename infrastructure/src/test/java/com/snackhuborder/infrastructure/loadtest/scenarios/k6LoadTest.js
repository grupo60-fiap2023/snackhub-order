import http from 'k6/http';
import { sleep } from 'k6';

export let options = {
  stages: [
    { duration: '10s', target: 2 }
  ],
};

export default function () {
  let order = {
    customerId: 1,
    orderItems: [
      {
        name: 'Hambúrguer',
        price: 35.99,
        quantity: 2,
        category: 'SNACK',
      },
    ],
    orderIdentifier: 'Maria',
    observation: 'Sem molho',
  };

  let params = {
    headers: {
      'Content-Type': 'application/json',
    },
  };

  let response = http.post('http://localhost:8080/orders/createOrder', JSON.stringify(order), params);

  if (response.status !== 201) {
    console.error(`Erro na requisição. Status: ${response.status}, Body: ${response.body}`);
  }
  sleep(1);
}