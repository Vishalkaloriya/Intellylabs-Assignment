// RegistrationForm.test.js
import React from 'react';
import { render, fireEvent, waitFor, screen } from '@testing-library/react';
import { BrowserRouter as Router } from 'react-router-dom';
import Register from './Register';
import axios from 'axios';

jest.mock('axios');

describe('Register', () => {
  beforeEach(() => {
    axios.post.mockClear();
  });

  test('renders the form fields', () => {
    render(
      <Router>
        <Register />
      </Router>
    );
    expect(screen.getByPlaceholderText('Enter Username')).toBeInTheDocument();
    expect(screen.getByPlaceholderText('Enter email')).toBeInTheDocument();
    expect(screen.getByPlaceholderText('Enter Password')).toBeInTheDocument();
  });

  test('registers a new user', async () => {
    axios.post.mockResolvedValueOnce({ data: 'User Registration Successful' });
    render(
      <Router>
        <Register />
      </Router>
    );

    fireEvent.change(screen.getByPlaceholderText('Enter Username'), { target: { value: 'testuser' } });
    fireEvent.change(screen.getByPlaceholderText('Enter email'), { target: { value: 'test@example.com' } });
    fireEvent.change(screen.getByPlaceholderText('Enter Password'), { target: { value: 'testpassword' } });

    fireEvent.click(screen.getByRole('button', { name: 'Submit' }));

    await waitFor(() => {
      expect(axios.post).toHaveBeenCalledWith('http://localhost:8085/api/v1/user/save', {
        username: 'testuser',
        email: 'test@example.com',
        password: 'testpassword',
      });
    });
  });

});