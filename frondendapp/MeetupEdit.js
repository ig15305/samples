import React, { Component } from 'react';

import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from './AppNavbar';

class MeetupEdit extends Component {

	emptyItem = {
			name: '',
			location: '',
			attendees: ''
	};

	constructor(props) {
		super(props);
		this.state = {
				item: this.emptyItem
		};
		this.handleChange = this.handleChange.bind(this);
		this.handleSubmit = this.handleSubmit.bind(this);
	}

	async componentDidMount() {
		if (this.props.match.params.id !== 'new') {
			const meetup = await (await fetch(`/api/meetup/${this.props.match.params.id}`)).json();
			this.setState({item: meetup});
			if(this.props.match.params.id) {
				const users = await (await fetch(`/api/meetup/${this.props.match.params.id}/users`)).json();
				this.setState({
					attendees: users.map((user, i) => (
							<li key={i} className="list-group-item">{user.name}</li>
					))
				});
			}
		}
	}

	handleChange(event) {
		const target = event.target;
		const value = target.value;
		const name = target.name;
		let item = {...this.state.item};
		item[name] = value;
		this.setState({item});
	}

	async handleSubmit(event) {
		event.preventDefault();
		const {item} = this.state;
		console.log('handle Submit' + item.id);
		if(item.id) {
			await fetch('/api/meetup/{item.id}', {
				method: 'PUT',
				headers: {
					'Accept': 'application/json',
					'Content-Type': 'application/json'
				},
				body: JSON.stringify(item),
			});
			this.props.history.push('/meetups');
		} else {
			await fetch('/api/meetup', {
				method: 'POST',
				headers: {
					'Accept': 'application/json',
					'Content-Type': 'application/json'
				},
				body: JSON.stringify(item),
			});
			this.props.history.push('/meetups');
		}
	}

	render() {
		const {item} = this.state;
		const title = <h2>{item.id ? 'Edit Meetup' : 'Add Meetup'}</h2>;

		return <div>
		<AppNavbar/>
		<Container>
		{title}
		<Form onSubmit={this.handleSubmit}>
		<FormGroup>
			<Label for="name">Name</Label>
			<Input type="text" name="name" id="name" value={item.name || ''}
				onChange={this.handleChange} autoComplete="name"/>
		</FormGroup>
		<FormGroup>
			<Label for="location">Location</Label>
			<Input type="text" name="location" id="location" value={item.location || ''}
				onChange={this.handleChange} autoComplete="location"/>
		</FormGroup>            
		<FormGroup>
			<Label for="attendees">Attendees</Label>
			<div>  <ul className="list-group list-group-flush">
			<Link to={'this.state.attendees'}>{this.state.attendees} </Link>
			</ul> </div>            		
		</FormGroup>
		<FormGroup>
			<Button color="primary" type="submit">Add Attendees</Button>{' '}
			<Button color="primary" type="submit">Save</Button>{' '}
			<Button color="secondary" tag={Link} to="/meetups">Cancel</Button>
		</FormGroup>
		</Form>
		</Container>
		</div>
	}
}

export default withRouter(MeetupEdit);

