import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';

class MeetupList extends Component {

  constructor(props) {
    super(props);
    this.state = {
    	meetups: [], 
    	users: [],
    	isLoading: true
    };
    this.remove = this.remove.bind(this);
  }

  componentDidMount() {
	console.log(this.state.id);
    if (this.state.id === -1) {
        return;
    }
    this.setState({isLoading: true});

    fetch('api/meetups')
      .then(response => response.json())
      .then(data => this.setState({meetups: data, isLoading: false}));
    fetch('api/users')
    .then(response => response.json())
    .then(data => this.setState({users: data, isLoading: false}));
  }

  async remove(id) {
    await fetch(`/api/meetup/${id}`, {
      method: 'DELETE',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      }
    }).then(() => {
      let updatedMeetups = [...this.state.meetups].filter(i => i.id !== id);
      this.setState({meetups: updatedMeetups});
    });
  }

  render() {
    const {meetups, isLoading} = this.state;

    if (isLoading) {
      return <p>Loading...</p>;
    }

    const meetupList = meetups.map(meetup => {
      const alist = meetup.attendees.map(user => user.name).join(", ");
      return <tr key={meetup.id}>
        <td style={{whiteSpace: 'nowrap'}}>{meetup.name}</td>
        <td>{meetup.location}</td>
        <td>{alist}</td>
        <td>
          <ButtonGroup>
            <Button size="sm" color="primary" tag={Link} to={"/meetups/" + meetup.id}>Edit</Button>
            <Button size="sm" color="danger" onClick={() => this.remove(meetup.id)}>Delete</Button>
          </ButtonGroup>
        </td>
      </tr>
    });

    return (
      <div>
        <AppNavbar/>
        <Container fluid>
          <div className="float-right">
            <Button color="success" tag={Link} to="/meetup/new">Add Meetup</Button>
          </div>
          <h3> Meetups</h3>
          <Table className="mt-4">
            <thead>
            <tr>
              <th width="20%">Name</th>
              <th width="20%">Location</th>
              <th>Attendees</th>
              <th width="10%">Actions</th>
            </tr>
            </thead>
            <tbody>
            {meetupList}
            </tbody>
          </Table>
        </Container>
      </div>
    );
  }
}

export default MeetupList;