// Login.test.js
import React from 'react';
import { render, fireEvent, waitFor, screen } from '@testing-library/react';
import { BrowserRouter as Router } from 'react-router-dom';
import Login from './Login';
import axios from 'axios';

jest.mock('axios');

describe('Login', () => {
  beforeEach(() => {
    axios.post.mockClear();
  });

  test('renders the form fields', () => {
    render(
      <Router>
        <Login />
      </Router>
    );
    expect(screen.getByPlaceholderText('Enter your email')).toBeInTheDocument();
    expect(screen.getByPlaceholderText('Enter password')).toBeInTheDocument();
  });

  test('logs in a user', async () => {
    axios.post.mockResolvedValueOnce({ data: { message: 'Login Success' } });
    render(
      <Router>
        <Login />
      </Router>
    );

    fireEvent.change(screen.getByPlaceholderText('Enter your email'), { target: { value: 'test@example.com' } });
    fireEvent.change(screen.getByPlaceholderText('Enter password'), { target: { value: 'testpassword' } });

    fireEvent.click(screen.getByRole('button', { name: 'Login' }));

    await waitFor(() => {
      expect(axios.post).toHaveBeenCalledWith('http://localhost:8085/api/v1/user/login', {
        email: 'test@example.com',
        password: 'testpassword',
      });
    });
  });

});