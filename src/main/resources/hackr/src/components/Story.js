import React, {Component} from 'react';
import PropTypes from 'prop-types';
import TimeAgo from 'react-timeago';

class Story extends Component {
    constructor(props) {
        super(props);
    }

    getHighlightingHTML() {
        return {__html: this.props.highlighting[Object.keys(this.props.highlighting)[0]][0]};

    }

    getURL() {
        if (this.props.data.url) {
            return this.props.data.url;
        } else {
            return this.getHNStoryURL();
        }

    }


    getHNStoryURL() {
        return "https://news.ycombinator.com/item?id=" + this.props.data.id;
    }

    getHNUserURL() {
        return "https://news.ycombinator.com/user?id=" + this.props.data.author;
    }


    render() {
        return (
            <div className="card">
                <div className="card-body">
                    <div>
                        <a href={this.getURL()} target="_blank">
                            {"title" in this.props.highlighting ?
                                <h2 dangerouslySetInnerHTML={this.getHighlightingHTML()}/> :
                                <h2>{this.props.data.title}</h2>}
                        </a>
                    </div>
                    <div>
                        <a href={this.getURL()} target="_blank">{this.getURL()}</a>
                    </div>

                    <div>
                        <a href={this.getHNUserURL()} target="_blank">{this.props.data.author}</a> | <a
                        href={this.getHNStoryURL()} target="_blank">{this.props.data.score} points</a> | <a
                        href={this.getHNStoryURL()} target="_blank"><TimeAgo date={this.props.data.time}/></a>
                    </div>

                    <div>
                        {"text" in this.props.highlighting ?
                            <span dangerouslySetInnerHTML={this.getHighlightingHTML()}/> :
                            this.props.data.text ? <span>{this.props.data.text}</span> : <span/>}
                    </div>
                </div>
            </div>
        );
    }
}

Story.propTypes = {
    data: PropTypes.object.isRequired,
    highlighting: PropTypes.object.isRequired
};

export default Story;